package es.ruben.pruebas.quartz;

import org.slf4j.Logger;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class HolaMundoQuartzJob extends QuartzJobBean  {
	
	private static Logger logger = LoggerFactory.getLogger(HolaMundoQuartzJob.class);

	public HolaMundoQuartzJob() {
		logger.debug("Creando objeto HolaMundoQuartzJob");
	}
	
	private int variablePrueba=-1;
	
	public int getVariablePrueba() {
		return variablePrueba;
	}

	public void setVariablePrueba(int variablePrueba) {
		this.variablePrueba = variablePrueba;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.debug("HolaMundo desde HolaMundoQuartzJob. VariablePrueba="+ variablePrueba);
//		System.out.println("log4j.configurationFile: "+System.getProperty("log4j.configurationFile"));
	}

}
