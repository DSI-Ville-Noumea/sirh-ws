package nc.noumea.mairie.model.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Service
public class ReportingService implements IReportingService {

	@Autowired
	@Qualifier("reportingBaseUrl")
	private String reportingBaseUrl;

	@Autowired
	@Qualifier("reportServerPath")
	private String reportServerPath;

	private static final String REPORT_PAGE = "frameset";
	private static final String PARAM_REPORT = "__report";
	private static final String PARAM_FORMAT = "__format";
	private static final String PARAM_LOCALE = "__locale";

	@Override
	public byte[] getFichePosteReportAsByteArray(int idFichePoste) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("idFichePoste", String.valueOf(idFichePoste));

		ClientResponse response = createAndFireRequest(map, "fichePoste.rptdesign", "PDF");

		return readResponseAsByteArray(response, map);
	}

	@Override
	public byte[] getTableauAvancementsReportAsByteArray(int idCap, int idCadreEmploi, boolean avisEAE)
			throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("idCap", String.valueOf(idCap));
		map.put("idCadreEmploi", String.valueOf(idCadreEmploi));
		map.put("avisEAE", String.valueOf(avisEAE));

		ClientResponse response = createAndFireRequest(map, "avctFonctCap.rptdesign", "PDF");

		return readResponseAsByteArray(response, map);
	}

	@Override
	public byte[] getArretesReportAsByteArray(String csvIdAgents, boolean isChangementClasse, int year,
			boolean isDetache) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("csvIdAgents", csvIdAgents);
		map.put("annee", String.valueOf(year));
		map.put("isDetache", isDetache ? "true" : "false");

		String reportName = isChangementClasse ? "avctChgtClasseArrete.rptdesign" : "avctDiffArrete.rptdesign";

		ClientResponse response = createAndFireRequest(map, reportName, "DOC");

		return readResponseAsByteArray(response, map);
	}

	public ClientResponse createAndFireRequest(Map<String, String> reportParameters, String reportName, String format) {

		Client client = Client.create();

		WebResource webResource = client.resource(reportingBaseUrl + REPORT_PAGE)
				.queryParam(PARAM_REPORT, reportServerPath + reportName).queryParam(PARAM_FORMAT, format)
				.queryParam(PARAM_LOCALE, "FR");

		for (String key : reportParameters.keySet()) {
			webResource = webResource.queryParam(key, reportParameters.get(key));
		}

		ClientResponse response = webResource.get(ClientResponse.class);

		return response;
	}

	public byte[] readResponseAsByteArray(ClientResponse response, Map<String, String> reportParameters)
			throws Exception {

		if (response.getStatus() != HttpStatus.OK.value()) {
			throw new Exception(
					String.format(
							"An error occured while querying the reporting server '%s' with ids '%s'. HTTP Status code is : %s.",
							reportingBaseUrl, getListOfParamsFromMap(reportParameters), response.getStatus()));
		}

		byte[] reponseData = null;
		File reportFile = null;

		try {
			reportFile = response.getEntity(File.class);
			reponseData = IOUtils.toByteArray(new FileInputStream(reportFile));
		} catch (Exception e) {
			throw new Exception("An error occured while reading the downloaded report.", e);
		} finally {
			if (reportFile != null && reportFile.exists())
				reportFile.delete();
		}

		return reponseData;
	}

	private String getListOfParamsFromMap(Map<String, String> reportParameters) {

		StringBuilder sb = new StringBuilder();

		for (String key : reportParameters.keySet()) {
			sb.append(String.format("[%s: %s] ", key, reportParameters.get(key)));
		}

		return sb.toString();
	}
	
	@Override
	public byte[] getFichePosteSIRHReportAsByteArray(int idFichePoste) throws Exception {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("idFichePoste", String.valueOf(idFichePoste));
		
		ClientResponse response = createAndFireRequest(map, "fichePosteSIRH.rptdesign", "DOC");
		
		return readResponseAsByteArray(response, map);
	}
}
