package gui;

import java.net.URL;
import java.util.Locale;

import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
public class ApplicationLauncher {
	public static DataAccess da;
    private static BLFacade appFacadeInterface;

	@SuppressWarnings("dep"
			+ "recation")
	public static void main(String[] args) {
		ConfigXML c=ConfigXML.getInstance();

		System.out.println(c.getLocale());

		Locale.setDefault(new Locale(c.getLocale()));

		System.out.println("Locale: "+Locale.getDefault());

		SelectGUI a = new SelectGUI();
		a.setVisible(true);


		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

			if (c.isBusinessLogicLocal()) {
				da = new DataAccess();
				appFacadeInterface=new BLFacadeImplementation(da);
			}

			else { //If remote

				 String serviceName= "http://"+c.getBusinessLogicNode() +":"+ c.getBusinessLogicPort()+"/ws/"+c.getBusinessLogicName()+"?wsdl";

				URL url = new URL(serviceName);


		        //1st argument refers to wsdl document above
				//2nd argument is service name, refer to wsdl document above
		        QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");

		        Service service = Service.create(url, qname);

		         appFacadeInterface = service.getPort(BLFacade.class);
			}

		}catch (Exception e) {
			System.out.println("Error in ApplicationLauncher: "+e.toString());
		}
	

	}
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}

	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}

}
