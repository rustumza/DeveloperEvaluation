package com.vestacare.developerevaluation1.servicies;

import com.vestacare.developerevaluation1.entities.Claim;
import com.vestacare.developerevaluation1.entities.Provider;
import com.vestacare.developerevaluation1.exception.DeveloperEvaluationException;
import com.vestacare.developerevaluation1.persistence.ClaimDao;
import com.vestacare.developerevaluation1.persistence.ProviderDao;
import com.vestacare.developerevaluation1.persistence.TransactionManager;
import com.vestacare.developerevaluation1.utils.PropertiesManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author rustu
 */
public class ProcessFilesService {
    public static final int COLUMN_C = 2;
    public static final int COLUMN_D = 3;
    public static final int COLUMN_G = 6;
    public static final int COLUMN_H = 7;
    public static final int COLUMN_I = 8;
    public static final int COLUMN_J = 9;
    public static final int COLUMN_K = 10;
    public static final int COLUMN_M = 12;
    public static final int COLUMN_N = 13;
    public static final int COLUMN_O = 14;
    public static final int COLUMN_P = 15;
    
    private static final int INITIAL_ROW =6;
    private static final String FOLDER_FILE_PATH_PROPERTY = "folder.path";
    private static final String DESTINATION_FOLDER_FILE_PATH = "/processed/";
    
        
    public void processFiles(){
        
        System.out.println("Starting to process files...");
        
        final String folderPath = PropertiesManager
                                        .getProperty(FOLDER_FILE_PATH_PROPERTY);
        
        if(folderPath == null || folderPath.isEmpty()){
            throw new DeveloperEvaluationException("The Path to the folder is empty.");
        }
        
        final String folderProcessedFiles = folderPath + DESTINATION_FOLDER_FILE_PATH;
        final File folder = new File(folderPath);
        
        final ProviderDao providerDao = new ProviderDao();
        final ClaimDao claimDao = new ClaimDao();
                
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                System.out.println(
                        String.format("Processing file '%s'.",fileEntry.getName()));
                
                if(!FilenameUtils.getExtension(fileEntry.getName()).equals("xls")){
                    System.out.println(String
                               .format("Invalid file extension for file %s'.",
                                       fileEntry.getName()));
                    continue;
                }
                
                // This map is used to avoid to search providers in the database
                // that were searched before.
                // It is created for each file because a possible rollback could
                // produce errors with the not persisted providers.
                final Map<String, Provider> providerMap = new HashMap();
                
                FileInputStream file = null;
                try {
                    
                    // For each file, it is used one transaction. So 
                    // one file process is an atomic action.
                    TransactionManager.beginTransaction();

                    // Reading the file
                    file = new FileInputStream(fileEntry);
                    		
                    final HSSFWorkbook  worbook = new HSSFWorkbook(file);
                    // Getting the sheet
                    final HSSFSheet sheet = worbook.getSheetAt(0);                    
                    
                    Row row;
                    
                    // Start to process each row.
                    // From row 7 the starts the information. The row count starts 
                    // in 0 so the first row to use is number 6
                    for(int count = INITIAL_ROW ; sheet.getRow(count)!=null ; count++){
                        row = sheet.getRow(count);
                        if(!validateRow(row)){
                            System.out.println(String.format("One or more columns are empty in row %d.",count));
                            continue;
                        }
                        final String providerTaxId = row.getCell(COLUMN_H).getStringCellValue();
                        Provider provider;
                        
                        // Look for the provider in the map.
                        if(providerMap.containsKey(providerTaxId)){
                            provider = providerMap.get(providerTaxId);
                        }else{
                            // If the provider is not in the map, look in the database.
                            provider = providerDao.findByTaxId(providerTaxId);
                            
                            if(provider == null){
                                // If the provider is not in the map or in the database,
                                // then it has to be created.
                                
                                // Validate Column I that is not validated in method validateRow()
                                final String providerRefNum = row.getCell(COLUMN_I) == null?"":
                                                            row.getCell(COLUMN_I).getStringCellValue();
                                
                                provider = new Provider(row.getCell(COLUMN_G).getStringCellValue(),
                                              row.getCell(COLUMN_H).getStringCellValue(),
                                                providerRefNum,
                                                  row.getCell(COLUMN_J).getStringCellValue(),
                                                    row.getCell(COLUMN_K).getStringCellValue(),
                                                      row.getCell(COLUMN_M).getStringCellValue(),
                                                        row.getCell(COLUMN_N).getStringCellValue(),
                                                          row.getCell(COLUMN_O).getStringCellValue());
                                
                                // Save the new provider.
                                providerDao.save(provider);
                            }
                            
                            // Add the provider to the map (the one that was 
                            // found in the database or the one that was created).
                            providerMap.put(providerTaxId, provider);
                        }
                        
                        // Check if the claim is saved in the database.
                        final boolean existClaim = 
                                claimDao.existClaimByClaimNumber(
                                        row.getCell(COLUMN_C).getStringCellValue());
                                                
                        if(existClaim){
                            System.out.println(
                                    String.format("Claim '%s' already exists in the database.",
                                            row.getCell(COLUMN_C)));
                        }else{
                            // If the claim is not in the database, then it is created and saved.
                            final Claim claim = new Claim(row.getCell(COLUMN_C).getStringCellValue(), 
                                    row.getCell(COLUMN_D).getStringCellValue(), 
                                        row.getCell(COLUMN_P).getStringCellValue(),
                                            provider);
                            claimDao.save(claim);
                        }
                    }
                    
                    // After finishing to process the file, commit the database transaction
                    // and close the file.
                    TransactionManager.commitTransaction();
                    file.close();
                    
                    // Move the file to the processed files folder.
                    final boolean success = fileEntry
                                              .renameTo(
                                                 new File(folderProcessedFiles + fileEntry.getName()));
                    if(!success){
                        System.out.println(
                            String.format("Error while moving file '%s'. It already exists on 'processed' folder.",
                                fileEntry.getName()));
                    }
                } catch (FileNotFoundException e){
                        System.out.println(String
                            .format("Error while opening file %s. The file does not exist.",
                                 fileEntry.getName()));
                        e.printStackTrace();
                        TransactionManager.rollbackTransaction();
                } catch (IOException e) {                        
                    System.out.println(String
                         .format("Error while processing file %s.",fileEntry.getName()));
                    e.printStackTrace();
                    TransactionManager.rollbackTransaction();
                } finally {
                    try {
                        if(file != null){
                            file.close();
                        }
                    } catch (IOException ex) {
                        System.out.println(String
                            .format("Error while closing file '%s'.",fileEntry.getName()));
                        ex.printStackTrace();
                    }
                }
            }
        }
        System.out.println("The files process has finished...");
        return;
    }
    
    /**
    * Returns true whether all columns are different than null.
    * 
    * Column I (Provider Ref Num) it could be null (empty) so is not validated.
    *
    * @param  row  
    * @return boolean    
    */
    private boolean validateRow(Row row){
        return !(row.getCell(COLUMN_C) == null || row.getCell(COLUMN_D) == null ||
                row.getCell(COLUMN_G) == null || row.getCell(COLUMN_H) == null ||
                row.getCell(COLUMN_J) == null || row.getCell(COLUMN_K) == null || 
                row.getCell(COLUMN_M) == null || row.getCell(COLUMN_N) == null || 
                row.getCell(COLUMN_O) == null || row.getCell(COLUMN_P) == null);
    }
}
