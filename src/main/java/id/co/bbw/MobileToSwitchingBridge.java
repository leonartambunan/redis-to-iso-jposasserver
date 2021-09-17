package id.co.bbw;

import org.jpos.q2.Q2;
import org.jpos.q2.iso.QMUX;
import org.jpos.util.NameRegistrar;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@ServletComponentScan
public class MobileToSwitchingBridge implements CommandLineRunner {

	public static void main(String[] args) {
		Q2 q2 = new Q2(args);
		q2.start();
		SpringApplication.run(MobileToSwitchingBridge.class, args);
	}

	public void run(String... args) throws Exception {
//		Q2 q2 = new Q2(args);
//		q2.start();
	}

	@Bean
	public QMUX qmux() throws Exception {
		return NameRegistrar.get("mux.digitalbank-mux");
	}

}
