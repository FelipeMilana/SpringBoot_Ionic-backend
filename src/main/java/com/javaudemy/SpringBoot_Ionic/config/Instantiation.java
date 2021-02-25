package com.javaudemy.SpringBoot_Ionic.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.javaudemy.SpringBoot_Ionic.domain.Adress;
import com.javaudemy.SpringBoot_Ionic.domain.CardPayment;
import com.javaudemy.SpringBoot_Ionic.domain.Category;
import com.javaudemy.SpringBoot_Ionic.domain.City;
import com.javaudemy.SpringBoot_Ionic.domain.Client;
import com.javaudemy.SpringBoot_Ionic.domain.Order;
import com.javaudemy.SpringBoot_Ionic.domain.OrderItem;
import com.javaudemy.SpringBoot_Ionic.domain.Payment;
import com.javaudemy.SpringBoot_Ionic.domain.Product;
import com.javaudemy.SpringBoot_Ionic.domain.SlipPayment;
import com.javaudemy.SpringBoot_Ionic.domain.State;
import com.javaudemy.SpringBoot_Ionic.domain.enums.ClientType;
import com.javaudemy.SpringBoot_Ionic.domain.enums.PaymentState;
import com.javaudemy.SpringBoot_Ionic.repositories.AdressRepository;
import com.javaudemy.SpringBoot_Ionic.repositories.CategoryRepository;
import com.javaudemy.SpringBoot_Ionic.repositories.CityRepository;
import com.javaudemy.SpringBoot_Ionic.repositories.ClientRepository;
import com.javaudemy.SpringBoot_Ionic.repositories.OrderItemRepository;
import com.javaudemy.SpringBoot_Ionic.repositories.OrderRepository;
import com.javaudemy.SpringBoot_Ionic.repositories.PaymentRepository;
import com.javaudemy.SpringBoot_Ionic.repositories.ProductRepository;
import com.javaudemy.SpringBoot_Ionic.repositories.StateRepository;

@Configuration
@Profile("test")
public class Instantiation implements CommandLineRunner {

	@Autowired
	private CategoryRepository catRepository;
	@Autowired
	private ProductRepository prodRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private AdressRepository adressRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private PaymentRepository payRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Category cat1 = new Category(null, "Informatica");
		Category cat2 = new Category(null, "Escritorio");
		Category cat3 = new Category(null, "Eletrônicos");
		Category cat4 = new Category(null, "Perfumaria");
		Category cat5 = new Category(null, "Móveis");
		
		Product p1 = new Product(null, "Computador", 2000.00);
		Product p2 = new Product(null, "Impressora", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);
		
		//ManyToMany associations
		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2));
		
		p1.getCategories().addAll(Arrays.asList(cat1));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2));
		p3.getCategories().addAll(Arrays.asList(cat1));
		
		catRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5));
		prodRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		
		State s1 = new State(null, "Minas Gerais");
		State s2 = new State(null, "São Paulo");
		
		City c1 = new City(null, "Uberlândia", s1);
		City c2 = new City(null, "São Paulo", s2);
		City c3 = new City(null, "Campinas", s2);
		
		//OneToMany associations
		s1.getCities().addAll(Arrays.asList(c1));
		s2.getCities().addAll(Arrays.asList(c2, c3));
		
		stateRepository.saveAll(Arrays.asList(s1, s2));
		cityRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Client cli1 = new Client(null, "Maria Silva", "maria@gmail.com", "36378912377", ClientType.PESSOAFISICA);
		
		cli1.getTelephones().addAll(Arrays.asList("27363323", "93836393"));
		
		Adress ad1 = new Adress(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, c1);
		Adress ad2 = new Adress(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		cli1.getAdresses().addAll(Arrays.asList(ad1, ad2));
		
		clientRepository.saveAll(Arrays.asList(cli1));
		adressRepository.saveAll(Arrays.asList(ad1, ad2));
		
		Order o1 = new Order(null, sdf.parse("30/09/2017 10:32"), cli1, ad1);
		Order o2 = new Order(null, sdf.parse("10/10/2017 19:35"), cli1, ad2);
		
		Payment pay1 =  new CardPayment(null, PaymentState.QUITADO, o1, 6);
		o1.setPayment(pay1);
		
		Payment pay2 =  new SlipPayment(null, PaymentState.PENDENTE, o2, sdf.parse("20/10/2017 00:00"), null);
		o2.setPayment(pay2);
		
		cli1.getOrders().addAll(Arrays.asList(o1, o2));
		
		orderRepository.saveAll(Arrays.asList(o1, o2));
		payRepository.saveAll(Arrays.asList(pay1, pay2));
		clientRepository.saveAll(Arrays.asList(cli1));
		
		OrderItem oi1 =  new OrderItem(o1, p1, 0.00, 1, 2000.00);
		OrderItem oi2 =  new OrderItem(o1, p3, 0.00, 2, 80.00);
		OrderItem oi3 =  new OrderItem(o2, p2, 100.00, 1, 800.00);
		
		o1.getItems().addAll(Arrays.asList(oi1,oi2));
		o2.getItems().addAll(Arrays.asList(oi3));
		
		p1.getItems().addAll(Arrays.asList(oi1));
		p2.getItems().addAll(Arrays.asList(oi3));
		p3.getItems().addAll(Arrays.asList(oi2));
		
		orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3));
		orderRepository.saveAll(Arrays.asList(o1, o2));
		prodRepository.saveAll(Arrays.asList(p1, p2, p3));
	}
}
