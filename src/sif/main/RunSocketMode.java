package sif.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.log4j.Logger;

import sif.IO.ReportFormat; 
import sif.IO.spreadsheet.InvalidSpreadsheetFileException;
import sif.IO.xml.SifMarshaller;
import sif.frontOffice.FrontDesk;
import sif.model.policy.DynamicPolicy;
import sif.model.policy.PolicyList;

public class RunSocketMode{
	private int clientPort;
	private Logger logger = Logger.getLogger(getClass());
	
	public RunSocketMode(int port){
		clientPort = port;
	}

	public void blockingListening() {
		Socket clientSocket = null;

		try {

			clientSocket = new Socket(InetAddress.getLoopbackAddress(),
					clientPort);


			while (true) {

				/*
				 * Read the policy file
				 */
				String policyFile = Utils.readString(clientSocket);
				if (policyFile.isEmpty()){
					break;
				}
				if (Application.isDebug()) {
					BufferedWriter out = new BufferedWriter(new FileWriter(new File("C:\\Spreadsheet Inspection Framework\\dynPol.xml")));
					out.write(policyFile);
					out.close();
				}
				/*
				 * Read the spreadsheet file, currently disabled as it's error prone
				 */
				//				byte[] spreadsheetContent = Utils
				//						.readBytes(clientSocket);
				//				File spreadsheetFile = Utils
				//						.writeToTempFile(spreadsheetContent);

				/*
				 * Generate the report
				 */
				String requestName = "Programmatic Request";

				FrontDesk desk = FrontDesk.getInstance();

				PolicyList policyList = SifMarshaller
						.unmarshal(new StringReader(policyFile));

				DynamicPolicy policy = policyList.getCompletePolicy();

				String filename = policy.getSpreadsheetFileName();
				File spreadsheetFile = new File(filename);
				desk.createAndRunDynamicInspectionRequest(requestName,
						spreadsheetFile, policy);

				String xmlReport = desk
						.createInspectionReport(ReportFormat.XML);
				if (Application.isDebug()){
					BufferedWriter out = new BufferedWriter(new FileWriter(new File("C:\\Spreadsheet Inspection Framework\\findings.xml")));
					out.write(xmlReport);
					out.close();
				}
				/*
				 * Send the report
				 */
				Utils.writeString(clientSocket, xmlReport);

			}

		} catch (IOException e){
			// silently ignore the IOException when it is closed
		} catch (InvalidSpreadsheetFileException e) {
			if (Application.isDebug()){
				// Print all stack traces
				logger.info("", e);
				for (Throwable e2 : e.getAdditional()){
					logger.info("", e2);
				}
				// show a window with the exceptions from the application
				DebugConsole con = new DebugConsole();
				if (e.getCause() != null){
					con.addStackTrace(e.getCause());
				}
				con.addStackTrace(e);

				for (Throwable e2 : e.getAdditional()){
					con.addStackTrace(e2);
					if (e2.getCause() != null)
						con.addStackTrace(e2.getCause());
				}
				// Close the socket so SIFEI can continue to work, or rather fail
				// the parsing due to no root element
				// TODO: report why it failed
				if (!clientSocket.isClosed()){
					try {
						clientSocket.close();
					} catch (IOException e1) {}
				}
				new Thread(con).start();
			} else {
				System.exit(Application.APPLICATIONERROR);
			}
		} catch (Exception e) {
			if (Application.isDebug()){
				// show a window with the exceptions from the application
				logger.info("", e);
				
				DebugConsole con = new DebugConsole();
				if (e.getCause() != null)
					con.addStackTrace(e.getCause());
				con.addStackTrace(e);
				// Close the socket so SIFEI can continue to work, or rather fail
				// the parsing due to no root element
				// TODO: report why it failed
				if (!clientSocket.isClosed()){
					try {
						clientSocket.close();
					} catch (IOException e1) {}
				}
				new Thread(con).start();
			}
		}
	}
}