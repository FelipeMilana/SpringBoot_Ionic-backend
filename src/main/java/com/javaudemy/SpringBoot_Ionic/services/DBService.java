package com.javaudemy.SpringBoot_Ionic.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.javaudemy.SpringBoot_Ionic.domain.Address;
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
import com.javaudemy.SpringBoot_Ionic.domain.enums.Profile;
import com.javaudemy.SpringBoot_Ionic.repositories.AddressRepository;
import com.javaudemy.SpringBoot_Ionic.repositories.CategoryRepository;
import com.javaudemy.SpringBoot_Ionic.repositories.CityRepository;
import com.javaudemy.SpringBoot_Ionic.repositories.ClientRepository;
import com.javaudemy.SpringBoot_Ionic.repositories.OrderItemRepository;
import com.javaudemy.SpringBoot_Ionic.repositories.OrderRepository;
import com.javaudemy.SpringBoot_Ionic.repositories.PaymentRepository;
import com.javaudemy.SpringBoot_Ionic.repositories.ProductRepository;
import com.javaudemy.SpringBoot_Ionic.repositories.StateRepository;

@Service
public class DBService {

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
	private AddressRepository adressRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private PaymentRepository payRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;

	public void instantiateDatabase() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Category cat1 = new Category(null, "Informática");
		cat1.setImageUrl("https://dl.dropboxusercontent.com/s/hb4b6y3m5gf5l03/cat1.jpg?dl=0");
		Category cat2 = new Category(null, "Escritório");
		cat2.setImageUrl("https://dl.dropboxusercontent.com/s/2mxhqr3hdqof6vf/cat2.jpg?dl=0");
		Category cat3 = new Category(null, "Cama, mesa e banho");
		cat3.setImageUrl("https://dl.dropboxusercontent.com/s/k46pn8q1sf5az4h/cat3.jpg?dl=0");
		Category cat4 = new Category(null, "Eletrônicos");
		cat4.setImageUrl("https://dl.dropboxusercontent.com/s/89ja81zx07ddc5k/cat4.jpg?dl=0");
		Category cat5 = new Category(null, "Jardinagem");
		cat5.setImageUrl("https://dl.dropboxusercontent.com/s/3ccyi6qy17bgp2i/cat5.jpg?dl=0");
		Category cat6 = new Category(null, "Decoração");
		cat6.setImageUrl("https://dl.dropboxusercontent.com/s/iklugfmah59gq0b/cat6.jpg?dl=0");
		Category cat7 = new Category(null, "Perfumaria");
		cat7.setImageUrl("https://dl.dropboxusercontent.com/s/ky57xyafgjqtd9i/cat7.jpg?dl=0");
		
		
		
		Product p1 = new Product(null, "Computador", 2000.00);
		p1.setImageUrl("https://dl.dropboxusercontent.com/s/umrv2egn0p81daf/prod1.jpg?dl=0");
		Product p2 = new Product(null, "Impressora", 800.00);
		p2.setImageUrl("https://dl.dropboxusercontent.com/s/zys8wm5fp7jq1r6/prod2.jpg?dl=0");
		Product p3 = new Product(null, "Mouse", 80.00);
		p3.setImageUrl("https://dl.dropboxusercontent.com/s/ka59w7o9bsjw4hw/prod3.jpg?dl=0");
		Product p4 = new Product(null, "Mesa de escritório", 300.00);
		p4.setImageUrl("https://dl.dropboxusercontent.com/s/82686asl50dmql3/prod4.jpg?dl=0");
		Product p5 = new Product(null, "Toalha", 50.00);
		p5.setImageUrl("https://dl.dropboxusercontent.com/s/nlvr7xnf7o0o1fv/prod5.jpg?dl=0");
		Product p6 = new Product(null, "Colcha", 200.00);
		p6.setImageUrl("https://dl.dropboxusercontent.com/s/6c8htj72mdd3gv1/prod6.jpg?dl=0");
		Product p7 = new Product(null, "TV true color", 1200.00);
		p7.setImageUrl("https://dl.dropboxusercontent.com/s/lc5wl5oqifr9i7z/prod7.jpg?dl=0");
		Product p8 = new Product(null, "Roçadeira", 800.00);
		p8.setImageUrl("https://dl.dropboxusercontent.com/s/p99n5d5c0hdmldy/prod8.jpg?dl=0");
		Product p9 = new Product(null, "Abajour", 100.00);
		p9.setImageUrl("https://dl.dropboxusercontent.com/s/hmf9r2ee9fzesy8/prod9.jpg?dl=0");
		Product p10 = new Product(null, "Pendente", 180.00);
		p10.setImageUrl("https://dl.dropboxusercontent.com/s/2slxsr6ecf73k6o/prod10.jpg?dl=0");
		Product p11 = new Product(null, "Shampoo", 90.00);
		p11.setImageUrl("https://dl.dropboxusercontent.com/s/l9f5w9kzefzxb81/prod11.jpg?dl=0");
		
		//ManyToMany associations
		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2, p4));
		cat3.getProducts().addAll(Arrays.asList(p5, p6));
		cat4.getProducts().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProducts().addAll(Arrays.asList(p8));
		cat6.getProducts().addAll(Arrays.asList(p9, p10));
		cat7.getProducts().addAll(Arrays.asList(p11));
		
		p1.getCategories().addAll(Arrays.asList(cat1, cat4));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategories().addAll(Arrays.asList(cat1, cat4));
		p4.getCategories().addAll(Arrays.asList(cat2));
		p5.getCategories().addAll(Arrays.asList(cat3));
		p6.getCategories().addAll(Arrays.asList(cat3));
		p7.getCategories().addAll(Arrays.asList(cat4));
		p8.getCategories().addAll(Arrays.asList(cat5));
		p9.getCategories().addAll(Arrays.asList(cat6));
		p10.getCategories().addAll(Arrays.asList(cat6));
		p11.getCategories().addAll(Arrays.asList(cat7));
		
		catRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		prodRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		
		
		State s1 = new State(null, "Minas Gerais");
		State s2 = new State(null, "São Paulo");
		State s3 = new State(null, "Rio de Janeiro");
		State s4 = new State(null, "Distrito Federal");
		State s5 = new State(null, "Bahia");
		State s6 = new State(null, "Ceará");
		State s7 = new State(null, "Amazonas");
		State s8 = new State(null, "Paraná");
		State s9 = new State(null, "Pernambuco");
		State s10 = new State(null, "Goiás");
		State s11 = new State(null, "Pará");
		State s12 = new State(null, "Rio Grande do Sul");
		State s13 = new State(null, "Maranhão");
		State s14 = new State(null, "Alagoas");
		State s15 = new State(null, "Mato Grosso do Sul");
		State s16= new State(null, "Espírito Santo");
		State s17 = new State(null, "Rio Grande do Norte");
		State s18 = new State(null, "Paraíba");
		State s19 = new State(null, "Santa Catarina");
		
		City c1 = new City(null, "Uberlândia", s1);
		City c2 = new City(null, "São Paulo", s2);
		City c3 = new City(null, "Campinas", s2);
		City c4 = new City(null, "Rio de Janeiro", s3);
		City c5 = new City(null, "Brasília", s4);
		City c6 = new City(null, "Salvador", s5);
		City c7 = new City(null, "Fortaleza", s6);
		City c8 = new City(null, "Belo Horizonte", s1);
		City c9 = new City(null, "Manaus", s7);
		City c10 = new City(null, "Curitiba", s8);
		City c11 = new City(null, "Recife", s9);
		City c12 = new City(null, "Goiânia", s10);
		City c13 = new City(null, "Belém", s11);
		City c14 = new City(null, "Porto Alegre", s12);
		City c15 = new City(null, "Guarulhos", s2);
		City c16 = new City(null, "São Luís", s13);
		City c17 = new City(null, "Niterói", s3);
		City c18 = new City(null, "Maceió", s14);
		City c19 = new City(null, "Duque de Caxias", s3);
		City c20 = new City(null, "Campo Grande", s15);
		City c21 = new City(null, "Vitória", s16);
		City c22 = new City(null, "Natal", s17);
		City c23 = new City(null, "João Pessoa", s18);
		City c24 = new City(null, "Florianópolis", s19);
		
		
		//OneToMany associations
		s1.getCities().addAll(Arrays.asList(c1, c8));
		s2.getCities().addAll(Arrays.asList(c2, c3, c15));
		s3.getCities().addAll(Arrays.asList(c4, c17, c19));
		s4.getCities().addAll(Arrays.asList(c5));
		s5.getCities().addAll(Arrays.asList(c6));
		s6.getCities().addAll(Arrays.asList(c7));
		s7.getCities().addAll(Arrays.asList(c9));
		s8.getCities().addAll(Arrays.asList(c10));
		s9.getCities().addAll(Arrays.asList(c11));
		s10.getCities().addAll(Arrays.asList(c12));
		s11.getCities().addAll(Arrays.asList(c13));
		s12.getCities().addAll(Arrays.asList(c14));
		s13.getCities().addAll(Arrays.asList(c16));
		s14.getCities().addAll(Arrays.asList(c18));
		s15.getCities().addAll(Arrays.asList(c20));
		s16.getCities().addAll(Arrays.asList(c21));
		s17.getCities().addAll(Arrays.asList(c22));
		s18.getCities().addAll(Arrays.asList(c23));
		s19.getCities().addAll(Arrays.asList(c24));
		
		stateRepository.saveAll(Arrays.asList(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, s17, s18, s19));
		cityRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18, c18,
				c19, c20, c21, c22, c23, c24));
		
		Client cli1 = new Client(null, "Maria Silva", "pedroaugs15@gmail.com", "36378912377", ClientType.PESSOAFISICA, encoder.encode("123"));
		cli1.getTelephones().addAll(Arrays.asList("27363323", "93836393"));
		cli1.setImageURL("https://dl.dropboxusercontent.com/s/orss648lmbhozbf/cp1.jpg?dl=0");
		
		Client cli2 = new Client(null, "Felipe Milana", "games.clorin@gmail.com", "87325165099", ClientType.PESSOAFISICA, encoder.encode("456"));
		cli2.addProfile(Profile.ADMIN);
		cli2.getTelephones().addAll(Arrays.asList("978884653", "654456457"));
		cli2.setImageURL("https://dl.dropboxusercontent.com/s/quxcwvwx0b9peul/cp2.jpg?dl=0");
		
		Address ad1 = new Address(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, c1);
		Address ad2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		Address ad3 = new Address(null, "Av. Floriano", "2106", null, "centro", "281777012", cli2, c2);
		
		cli1.getAddresses().addAll(Arrays.asList(ad1, ad2));
		cli2.getAddresses().addAll(Arrays.asList(ad3));
		
		clientRepository.saveAll(Arrays.asList(cli1, cli2));
		adressRepository.saveAll(Arrays.asList(ad1, ad2, ad3));
		
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
