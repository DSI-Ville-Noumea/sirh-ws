package nc.noumea.mairie.model.service;

import java.io.File;
import java.io.FileInputStream;

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
	
	@Override
	public byte[] getFichePosteReportAsByteArray(int idFichePoste) throws Exception {
		
		ClientResponse response = createAndFireRequest(idFichePoste);
		
		return readResponseAsByteArray(response, idFichePoste);
	}
	
	public ClientResponse createAndFireRequest(int id) {
		
		Client client = Client.create();

		WebResource webResource = client
				.resource(reportingBaseUrl + REPORT_PAGE)
				.queryParam(PARAM_REPORT, reportServerPath + "fichePoste.rptdesign")
				.queryParam(PARAM_FORMAT, "PDF")
				.queryParam("idFichePoste", String.valueOf(id));

		ClientResponse response = webResource.get(ClientResponse.class);
		
		return response;
	}

	public byte[] readResponseAsByteArray(ClientResponse response, int id) throws Exception {
		
		if (response.getStatus() != HttpStatus.OK.value()) {
			throw new Exception(
					String.format(
							"An error occured while querying the reporting server '%s' with id '%s'. HTTP Status code is : %s.",
							reportingBaseUrl, id, response.getStatus()));
		}
		
		byte[] reponseData = null;
		File reportFile = null;
		
		try {
			reportFile = response.getEntity(File.class);
			reponseData =  IOUtils.toByteArray(new FileInputStream(reportFile));
		} catch (Exception e) {
			throw new Exception("An error occured while reading the downloaded report.", e);
		} finally {
			if (reportFile != null && reportFile.exists())
				reportFile.delete();
		}
		
		return reponseData;
	}

}
