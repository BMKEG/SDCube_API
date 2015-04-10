/**
   SDCube Programming Library
   Software for the creation and manipulation of semantically-typed
   data hypercubes

   Copyright (C) 2011 Bjorn Millard <bjornmillard@gmail.com>

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU Lesser General Public License as
   published by the Free Software Foundation, either version 3 of the
   License, or (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with this program.  If not, see
   <http://www.gnu.org/licenses/>.
 */

package edu.harvard.sorgerlab.sdcubeio;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SDCubeTest {

	File root;

	@Before
	public void setUp() throws Exception {
		String tempPath = ClassLoader.getSystemResource("temp.txt").getFile();
		File tempFile = new File(tempPath);
		root = tempFile.getParentFile();
	}

	@After
	public void tearDown() throws Exception {
		int p = 0;
		p++;
	}

	@Test
	public void testWriteOut() {

		Float[] array = new Float[50];
		for (int r = 0; r < 50; r++)
			array[r] = new Float((float) (Math.random() * 1000));
		Double[][] matrix = new Double[8][4];
		for (int r = 0; r < matrix.length; r++)
			for (int c = 0; c < matrix[0].length; c++)
				matrix[r][c] = new Double((double) (Math.random() * 1000));
		String[] stringArr = new String[50];
		for (int r = 0; r < 50; r++)
			stringArr[r] = "ST=" + (Math.random() * 1000000);

		// Creating the ExpDesign
		ExpDesign_Description egf9 = new ExpDesign_Description("Treatment",
				"EGF", "1e-9", "M", "0", "min", null);
		ExpDesign_Description egf10 = new ExpDesign_Description("Treatment",
				"EGF", "1e-10", "M", "5", "min", null);
		ExpDesign_Description egf11 = new ExpDesign_Description("Treatment",
				"EGF", "1e-11", "M", "10", "min", null);
		ExpDesign_Description gef = new ExpDesign_Description("Treatment",
				"Gefitinib", "1e-6", "M", "-20", "min", null);
		ExpDesign_Description her = new ExpDesign_Description("Treatment",
				"HRG", "1e-9", "M", "0", "min", null);
		ExpDesign_Description perk = new ExpDesign_Description("Measurement",
				"pERK", "1:4000", "Dilution", "3", "day", null);

		ExpDesign_Model edm = new ExpDesign_Model(root.getPath());		
		edm.addDescription("egf9", egf9);
		edm.addDescription("egf10", egf10);
		edm.addDescription("egf11", egf11);
		edm.addDescription("gef", her);
		edm.addDescription("perk", perk);

		// Creating 2 samples
		ExpDesign_Sample sam1 = new ExpDesign_Sample();
		sam1.addDescription(egf9);
		sam1.addDescription(gef);
		sam1.addDescription(perk);

		ExpDesign_Sample sam2 = new ExpDesign_Sample();
		sam2.addDescription(egf10);
		sam2.addDescription(gef);

		ExpDesign_Sample sam3 = new ExpDesign_Sample();
		sam3.addDescription(egf11);
		sam3.addDescription(gef);

		ExpDesign_Sample sam4 = new ExpDesign_Sample();
		sam4.addDescription(her);
		sam4.addDescription(gef);

		ExpDesign_Sample sam5 = new ExpDesign_Sample();
		sam5.addDescription(gef);

		ExpDesign_Sample sam6 = new ExpDesign_Sample();
		sam6.addDescription(her);
		sam6.addDescription(perk);
		
		SDCube sdc = new SDCube();
		String sdcPath = root + "/SDCubeTest.sdc";
		sdc.setPath(sdcPath);

		// Creating the H5 data
		SDCube_DataModule d1 = new SDCube_DataModule(sdcPath + "/Data.h5",
				"./Children/0");
		d1.addData(new Data_1D(array, "FLOAT", "floatARR"));
		sdc.addSample(new SDCube_Sample(d1, sam1, "ID_1"));

		// Creating the H5 data
		SDCube_DataModule d2 = new SDCube_DataModule(sdcPath + "/Data.h5",
				"./Children/1");
		d2.addData(new Data_1D(array, "FLOAT", "floatARR"));
		sdc.addSample(new SDCube_Sample(d2, sam2, "ID_2"));

		// Creating the H5 data
		SDCube_DataModule d3 = new SDCube_DataModule(sdcPath + "/Data.h5",
				"./Children/2");
		d3.addData(new Data_1D(array, "FLOAT", "floatARR"));
		sdc.addSample(new SDCube_Sample(d3, sam3, "ID_3"));

		// Creating the H5 data
		SDCube_DataModule d4 = new SDCube_DataModule(sdcPath + "/Data.h5",
				"./Children/3");
		d4.addData(new Data_1D(array, "FLOAT", "floatARR"));
		sdc.addSample(new SDCube_Sample(d4, sam4, "ID_4"));

		// Creating the H5 data
		SDCube_DataModule d5 = new SDCube_DataModule(sdcPath + "/Data.h5",
				"./Children/4");
		d5.addData(new Data_1D(array, "FLOAT", "floatARR"));
		sdc.addSample(new SDCube_Sample(d5, sam5, "ID_5"));

		// Creating the H5 data
		SDCube_DataModule d6 = new SDCube_DataModule(sdcPath + "/Data.h5",
				"./Children/5");
		d6.addData(new Data_1D(array, "FLOAT", "floatARR"));
		sdc.addSample(new SDCube_Sample(d6, sam6, "ID_6"));

		
		
		// Trying to write it out
		try {
			sdc.write();
		} catch (H5IO_Exception e) {
			System.err.println("ERROR writing SDCube: " + sdc.getPath());
			e.printStackTrace();
		}

	}

	@Test
	public void testReadIn() {

		SDCube sdc2 = new SDCube();
		String sdcPath = root + "/SDCubeTest.sdc";
		
		try {
			sdc2.load(sdcPath);
		} catch (H5IO_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] tagNames = new String[] { "EGF", "HRG" };
		ArrayList<SDCube_Sample> selectSamples = SDCube
				.getSamplesWithDescriptorNames_OR(sdcPath, tagNames);

		for (int i = 0; i < selectSamples.size(); i++) {
			System.out.println(selectSamples.get(i));
		}

	}

}
