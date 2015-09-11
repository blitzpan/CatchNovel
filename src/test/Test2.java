package test;
import org.apache.log4j.Logger;

public class Test2 {
	private static Logger logger = Logger.getLogger(Test2.class);

	public void run() {
		// ��¼debug�������Ϣ
		logger.debug("This is debug message.");
		// ��¼info�������Ϣ
		logger.info("This is info message.");
		// ��¼error�������Ϣ
		logger.error("This is error message.");
		try{
			int i = 5/0;
		}catch(Exception e){
			logger.error("��ӡgetMessage=" + e.getMessage());
			logger.error("��ӡgetStackTrace=" + e.getStackTrace());
			logger.error("��ӡe=" + e);
			logger.error("��ӡerror(e,e)=");
			logger.error(e, e);
		}
	}

	public static void main(String[] args) {
		Test2 t = new Test2();
		t.run();
	}
}
