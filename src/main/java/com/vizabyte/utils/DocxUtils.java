/*
 * Copyright 2017, Vizabyte LLP and the original author(s) and contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * 
 */
package com.vizabyte.utils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.docx4j.Docx4J;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.io.SaveToZipFile;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

public class DocxUtils {
	
	/**
	 * Replaces variables represented as ${variable-name} inside an input file
	 * in the .docx file format to the corresponding values specified by the
	 * variables names as the key in the input map.
	 * 
	 * @param documentPart
	 *            - the result of the variable replacement
	 * @param mappings
	 *            - the variable - value mappings
	 * @return - the result as a MainDocumentPart
	 * @throws JAXBException
	 *             - when errors are encountered while replacing variables in
	 *             the input file
	 * @throws Docx4JException
	 *             - when errors are encountered while reading the input .docx
	 *             file
	 */
	public static MainDocumentPart replaceVariables(MainDocumentPart documentPart, Map<String, String> mappings) throws JAXBException, Docx4JException{
		if (documentPart == null || mappings == null){
			throw new NullPointerException();
		}
		documentPart.variableReplace(mappings);
		return documentPart;
	}
	
	/**
	 * Replaces variables represented as ${variable-name} inside an input file
	 * in the .docx file format to the corresponding values specified by the
	 * variables names as the key in the input map and replaces the contents of
	 * the original file with the result.
	 * 
	 * @param docxFile
	 *            - input file in the .docx format
	 * @param mappings
	 *            - the variable - value mappings
	 * @return - the result as a file object
	 * @throws JAXBException
	 *             - when errors are encountered while replacing variables in
	 *             the input file
	 * @throws Docx4JException
	 *             - when errors are encountered while reading the input .docx
	 *             file
	 * @throws IOException
	 *             - If an I/O error occurs, which is possible because the
	 *             construction of the canonical pathname may require filesystem
	 *             queries
	 */
	@SuppressWarnings("deprecation")
	public static File replaceVariables(File docxFile, Map<String, String> mappings) throws JAXBException, Docx4JException, IOException{
		if (docxFile == null || mappings == null){
			throw new NullPointerException();
		}
		
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
				.load(docxFile);
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
		
		documentPart.variableReplace(mappings);
		
		SaveToZipFile saver = new SaveToZipFile(wordMLPackage);
		saver.save(docxFile.getCanonicalPath());
		return docxFile;
	}

	/**
	 * Replaces variables represented as ${variable-name} inside an input file
	 * in the .docx file format to the corresponding values specified by the
	 * variables names as the key in the input map and saves the result in the
	 * file location specified by the outFile.
	 * 
	 * @param docxFile
	 *            - input file in the .docx format
	 * @param mappings
	 *            - the variable - value mappings
	 * @param outFile
	 *            - output file
	 * @return - the output file
	 * @throws JAXBException
	 *             - when errors are encountered while replacing variables in
	 *             the input file
	 * @throws Docx4JException
	 *             - when errors are encountered while reading the input .docx
	 *             file
	 */
	@SuppressWarnings("deprecation")
	public static File replaceVariables(File docxFile, Map<String, String> mappings, File outFile) throws Docx4JException, JAXBException{
		if (docxFile == null || mappings == null){
			throw new NullPointerException();
		}
		
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
				.load(docxFile);
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
		
		documentPart.variableReplace(mappings);
		
		SaveToZipFile saver = new SaveToZipFile(wordMLPackage);
		saver.save(outFile);
		return outFile;
	}
	
	/**
	 * Replaces variables represented as ${variable-name} inside an input stream
	 * on a .docx file to the corresponding values specified by the variables
	 * names as the key in the input map and returns the result as a byte array.
	 * 
	 * @param docxInputStream
	 *            - input stream on a .docx file
	 * @param mappings
	 *            - the variable - value mappings
	 * @return - the result as a byte array
	 * @throws JAXBException
	 *             - when errors are encountered while replacing variables in
	 *             the input file
	 * @throws Docx4JException
	 *             - when errors are encountered while reading the input .docx
	 *             file
	 */
	public static byte[] replaceVariables(InputStream docxInputStream, Map<String, String> mappings) throws JAXBException, Docx4JException, IOException{
		if (docxInputStream == null || mappings == null){
			throw new NullPointerException();
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
				.load(docxInputStream);
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
		
		documentPart.variableReplace(mappings);
		
		SaveToZipFile saver = new SaveToZipFile(wordMLPackage);
		saver.save(baos);
		return baos.toByteArray();
	}
	
	public static void toPdf(InputStream isDocument, OutputStream pdfStream) throws Docx4JException {
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(isDocument);
		FOSettings foSettings = Docx4J.createFOSettings();
		foSettings.setWmlPackage(wordMLPackage);
		Docx4J.toFO(foSettings, pdfStream, Docx4J.FLAG_EXPORT_PREFER_XSL);
		
		// Clean up, so any ObfuscatedFontPart temp files can be deleted
		if (wordMLPackage.getMainDocumentPart().getFontTablePart() != null) {
			wordMLPackage.getMainDocumentPart().getFontTablePart().deleteEmbeddedFontTempFiles();
		}
	}
			
	public static void toPdf(File inputDocxFile, File outputPdfFile) throws FileNotFoundException, Docx4JException{
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try{
				fis = new FileInputStream(inputDocxFile);
				fos = new FileOutputStream(outputPdfFile);
				toPdf(fis, fos);
		}finally{
			IOUtils.closeQuietly(fis);
			IOUtils.closeQuietly(fos);
		}
		
	}
	
	public static void toPdf(String docxFilePath, String pdfFilePath) throws FileNotFoundException, Docx4JException{
		File inputFile = new File(docxFilePath);
		File outputFile = new File(pdfFilePath);
		toPdf(inputFile, outputFile);
	}

}
