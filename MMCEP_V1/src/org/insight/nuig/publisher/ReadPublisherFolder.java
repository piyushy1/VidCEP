/**
 * 
 */
package org.insight.nuig.publisher;

import java.io.File;

import org.insight.nuig.configurator.ConfigurationParameters;

/**
 * @author piyush
 * @date 25 Jul 2018
 * @time 11:22:08
 * @Institute Insight Centre for Data Analytics- NUIG
 * @Project: This file is part of the MMPS project owned by Sunstainable IT
 *           Group NUIG
 * 
 * @ClassDefinition
 * 
 */
public class ReadPublisherFolder {

	// call the file for publisher

	public static File[] getfile(File folder) {
		File[] listOfFiles = folder.listFiles();
		File[] new_listOfFiles = null;

		// presently each publisher is reading from its own file.
		// temporary solution:if no. of files is less then no. of publisher, then the
		// extra files will be added using loopback

		if (listOfFiles.length <= ConfigurationParameters.PUBLISHER_NUMBER) {
			int diff = (ConfigurationParameters.PUBLISHER_NUMBER - listOfFiles.length);
			new_listOfFiles = new File[listOfFiles.length + diff];
			System.arraycopy(listOfFiles, 0, new_listOfFiles, 0, listOfFiles.length);
			// new_listOfFiles=listOfFiles;
			for (int i = 0; i <= (diff - 1); i++) {
				new_listOfFiles[listOfFiles.length + i] = listOfFiles[0];
			}

		}

		if (new_listOfFiles != null) {

			return new_listOfFiles;

		} else {

			return listOfFiles;
		}

	}

}
