package com.javaudemy.SpringBoot_Ionic.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaudemy.SpringBoot_Ionic.domain.Address;
import com.javaudemy.SpringBoot_Ionic.domain.Client;
import com.javaudemy.SpringBoot_Ionic.domain.Order;
import com.javaudemy.SpringBoot_Ionic.domain.OrderItem;
import com.javaudemy.SpringBoot_Ionic.domain.Product;
import com.javaudemy.SpringBoot_Ionic.domain.SlipPayment;
import com.javaudemy.SpringBoot_Ionic.domain.dto.OrderInsertDTO;
import com.javaudemy.SpringBoot_Ionic.domain.dto.OrderItemDTO;
import com.javaudemy.SpringBoot_Ionic.domain.enums.PaymentState;
import com.javaudemy.SpringBoot_Ionic.repositories.OrderRepository;
import com.javaudemy.SpringBoot_Ionic.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;
	@Autowired
	private SlipService slipService;
	@Autowired
	private ProductService prodService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private AddressService adressService;
	
	public List<Order> findAll()	{
		return repository.findAll();
	}
	
	public Order findById(Integer id) {
		Optional<Order> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Order.class.getName()));
	}
	
	public Order insert(Order obj) {
		obj = repository.save(obj);
		return obj;
	}
	
	public Order fromDTO(OrderInsertDTO objDTO) {
		Client client = clientService.findById(objDTO.getClientId());
		Address adress = adressService.findById(objDTO.getDeliveryAddressId());
		
		if(!client.getAddresses().contains(adress)) {
			throw new ObjectNotFoundException("Este cliente não possui esse endereço cadastrado");
		}
		
		Order order = new Order(null, new Date(), client, adress);
		
		order.setPayment(objDTO.getPayment());
		order.getPayment().setState(PaymentState.PENDENTE);
		order.getPayment().setOrder(order);
		
		if(order.getPayment() instanceof SlipPayment) {
			SlipPayment pay = (SlipPayment) order.getPayment();
			slipService.fillPayment(pay, order.getInstant());
		}
		
		for(OrderItemDTO oiDTO: objDTO.getItems()) {
			Product prod = prodService.findById(oiDTO.getProductId());
			OrderItem oi = new OrderItem(order, prod, 0.0, oiDTO.getQuantity(), prod.getPrice());
			order.getItems().add(oi);
		}
		
		return order;
	}
}