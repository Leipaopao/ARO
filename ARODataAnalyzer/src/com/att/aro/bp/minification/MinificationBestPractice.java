/*
 *  Copyright 2013 AT&T
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
package com.att.aro.bp.minification;

import java.net.URI;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.HyperlinkEvent;

import com.att.aro.bp.BestPracticeDisplay;
import com.att.aro.bp.BestPracticeExport;
import com.att.aro.main.ApplicationResourceOptimizer;
import com.att.aro.model.TraceData;
import com.att.aro.model.TraceData.Analysis;
import com.att.aro.util.Util;

/**
 * Represents Minification Best Practice.
 */
public class MinificationBestPractice implements BestPracticeDisplay {

	private MinificationResultPanel resultPanel;

	@Override
	public String getOverviewTitle() {
		return Util.RB.getString("minification.title");
	}

	@Override
	public String getDetailTitle() {
		return Util.RB.getString("minification.detailedTitle");
	}

	@Override
	public boolean isSelfTest() {
		return false;
	}

	@Override
	public String getAboutText() {
		return Util.RB.getString("minification.desc");
	}

	@Override
	public URI getLearnMoreURI() {
		return URI.create(Util.RB.getString("minification.url"));
	}

	@Override
	public boolean isPass(TraceData.Analysis analysis) {
		return analysis.getMinificationAnalysis().isTestPassed();
	}

	@Override
	public String resultText(Analysis analysisData) {
		int numOfFiles = analysisData.getBestPractice().getNumberOfMinifyFiles();
		String key = isPass(analysisData) ? "minification.pass" : "minification.results";
		return MessageFormat.format(Util.RB.getString(key), numOfFiles);
	}

	@Override
	public void performAction(HyperlinkEvent h, ApplicationResourceOptimizer parent) {
	}

	@Override
	public List<BestPracticeExport> getExportData(Analysis analysisData) {

		int numberOfFiles = analysisData.getBestPractice().getNumberOfMinifyFiles();
		BestPracticeExport bpe;
		bpe = new BestPracticeExport(NumberFormat.getIntegerInstance().format(numberOfFiles), 
				                     Util.RB.getString("exportall.csvNumberOfMinifyFiles"));

		List<BestPracticeExport> result = new ArrayList<BestPracticeExport>(1);
		result.add(bpe);

		return result;
	}

	@Override
	public JPanel getTestResults() {
		if (this.resultPanel == null) {
			this.resultPanel = new MinificationResultPanel();
		}
		return this.resultPanel;
	}

}
