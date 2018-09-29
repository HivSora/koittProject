package jdbc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;


//디비 커넥션 풀을 준비하기 위한 컨텍스트 리스너 클래스
public class DBCPInitListener implements ServletContextListener {
		
	//시작할 때 우리의 디비 커넥션 풀을 셋팅하도록 하장
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//서블릿 컨텍스트를 받고
		ServletContext sc = sce.getServletContext();
		//서블릿 컨텍스트를 통해서 init파라미터(디비 정보가 있는 properties)를 받음
		//파일주소로 파일을 읽어와야한다. 시스템상의 주소
		String poolConfigFile = sc.getRealPath(sc.getInitParameter("poolConfigFile"));
		//String poolConfigFile = sc.getInitParameter("poolConfigFile");
		
		//파일주소로 프로퍼티스 객체에 파일에 있는 데이타를 넣을 것임.
		Properties prop = new Properties();
		try {
			prop.load(new FileReader(poolConfigFile));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("not found poolConfigFile", e);
		} catch (IOException e) {
			throw new RuntimeException("fail to read poolConfigFile", e);
		}
		//jdbc드라이버 로드
		loadJDBCDriver(prop);
		//커넥션 풀 초기화
		initConnectionPool(prop);
		
	}
	
	
	
	
	private void initConnectionPool(Properties prop) {
		try {
			//디비 접속 정보를 스트링으로 받음
			String connectUri = prop.getProperty("jdbcUri");
			String uname = prop.getProperty("dbUser");
			String passwd = prop.getProperty("dbpwd");
			
			//디비 정보를 인자로 넣고 커넥션을 만들어 주는 팩토리 객체 생성
			ConnectionFactory connFactory = new DriverManagerConnectionFactory(connectUri, uname, passwd);
			
			//풀에서 쓸수있는 커넥션을 만들어주는 팩토리에 커넥션 팩토리를 넣고 생성
			PoolableConnectionFactory poolableConnFactory = new PoolableConnectionFactory(connFactory,null);
			//커넥션이 유용한지 채크하기 위한 쿼리를 지정    (파일에 정의되어있는 값,없을시 적용될 기본값)
			poolableConnFactory.setValidationQuery(prop.getProperty("validationQuery","select 1"));
			//커넥션 풀의 설정 정보를 다루는 객체 생성, 설정정보 셋팅
			GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
			poolConfig.setTimeBetweenEvictionRunsMillis(1000L * 60L * 5L);
			poolConfig.setTestWhileIdle(true);
			poolConfig.setMinIdle(Integer.parseInt(prop.getProperty("minIdle","5")));
			poolConfig.setMaxTotal(Integer.parseInt(prop.getProperty("MaxTotal","50")));
			
			//커넥션 풀 생성
			GenericObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnFactory
					,poolConfig);
			poolableConnFactory.setPool(connectionPool);
			
			//쿨링 드라이버를 로드
			Class.forName(prop.getProperty("poolingDriver"));
			PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
			String poolName = prop.getProperty("poolName");
			driver.registerPool(poolName, connectionPool);
		}catch (Exception e) {
			throw new RuntimeException("not found pooling jdbc driver", e);
		}
	}
	
	private void loadJDBCDriver(Properties prop) {
		//프로퍼티에서 설정한 드라이버주소로 클래스 로드
		String driverClass = prop.getProperty("jdbcDriver");
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("fail to load JDBC Driver",e);
		}
	}
	



	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}


}
