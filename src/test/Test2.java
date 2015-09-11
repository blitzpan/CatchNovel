package test;
import org.apache.log4j.Logger;

public class Test2 {
	private static Logger logger = Logger.getLogger(Test2.class);

	public void run() {
		// 记录debug级别的信息
		logger.debug("This is debug message.");
		// 记录info级别的信息
		logger.info("This is info message.");
		// 记录error级别的信息
		logger.error("This is error message.");
		try{
			int i = 5/0;
		}catch(Exception e){
			logger.error("打印getMessage=" + e.getMessage());
			logger.error("打印getStackTrace=" + e.getStackTrace());
			logger.error("打印e=" + e);
			logger.error("打印error(e,e)=");
			logger.error(e, e);
		}
	}

	public static void main(String[] args) {
		Test2 t = new Test2();
		t.run();
	}
}
