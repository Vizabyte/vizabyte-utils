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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.io.SaveToZipFile;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

public class DocxUtils {
	
	public static void main(String[] args) throws Exception {

		// Input docx has variables in it: ${colour}, ${icecream}
		String inputfilepath = "/Users/vikas/Downloads/demo.docx";
		
		HashMap<String, String> mappings = new HashMap<String, String>();
		mappings.put("colour", "green");
		mappings.put("icecream", "chocolate");
		
		DocxUtils.replaceVariables(new java.io.File(inputfilepath), mappings);
	}

	/**
	 * Replaces variables inside an input file in the .docx file format to the
	 * corresponding values specified by the variables names as the key in the
	 * input map.
	 * 
	 * @param documentPart
	 *            - the result of the variable replacement
	 * @param mappings
	 *            - the variable - value mappings
	 * @return
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
	 * Replaces variables inside an input file in the .docx file format to the
	 * corresponding values specified by the variables names as the key in the
	 * input map and replaces the contents of the original file with the result.
	 * 
	 * @param docxFile
	 *            - input file in the .docx format
	 * @param mappings
	 *            - the variable - value mappings
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
	public static void replaceVariables(File docxFile, Map<String, String> mappings) throws JAXBException, Docx4JException, IOException{
		if (docxFile == null || mappings == null){
			throw new NullPointerException();
		}
		
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
				.load(docxFile);
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
		
		documentPart.variableReplace(mappings);
		
		SaveToZipFile saver = new SaveToZipFile(wordMLPackage);
		saver.save(docxFile.getCanonicalPath());
	}

	/**
	 * Replaces variables inside an input file in the .docx file format to the
	 * corresponding values specified by the variables names as the key in the
	 * input map and saves the result in the file location specified by the
	 * outFile.
	 * 
	 * @param docxFile
	 *            - input file in the .docx format
	 * @param mappings
	 *            - the variable - value mappings
	 * @param outFile
	 *            - output file
	 * @throws JAXBException
	 *             - when errors are encountered while replacing variables in
	 *             the input file
	 * @throws Docx4JException
	 *             - when errors are encountered while reading the input .docx
	 *             file
	 */
	@SuppressWarnings("deprecation")
	public static void replaceVariables(File docxFile, Map<String, String> mappings, File outFile) throws Docx4JException, JAXBException{
		if (docxFile == null || mappings == null){
			throw new NullPointerException();
		}
		
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
				.load(docxFile);
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
		
		documentPart.variableReplace(mappings);
		
		SaveToZipFile saver = new SaveToZipFile(wordMLPackage);
		saver.save(outFile);
	}
}
