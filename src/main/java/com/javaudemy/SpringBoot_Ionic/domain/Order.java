package com.javaudemy.SpringBoot_Ionic.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tb_order")
public class Order implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="dd/MM/yyyy HH:mm", timezone="GMT-3")
	private Date instant;
	
	//associations
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;
	
	@ManyToOne
	@JoinColumn(name = "deliveryAddress_id")
	private Address deliveryAddress;
	
	@OneToOne(mappedBy = "order", cascade=CascadeType.ALL)
	private Payment payment;
	
	@OneToMany(mappedBy = "id.order", cascade=CascadeType.PERSIST)
	private Set<OrderItem> items = new HashSet<>();
	
	public Order() {
	}
	
	public Order(Integer id, Date instant, Client client, Address deliveryAddress) {
		this.id = id;
		this.instant = instant; 
		this.client = client;
		this.deliveryAddress = deliveryAddress;
	}
	

	public Double getTotal() {
		return items.stream().mapToDouble(x -> x.getSubTotal()).sum();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstant() {
		return instant;
	}

	public void setInstant(Date instant) {
		this.instant = instant;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Address getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	
	public Set<OrderItem> getItems() {
		return items;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		NumberFormat nf =  NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		SimpleDateFormat sdf  =  new SimpleDateFormat("dd/MM/yyyy HH:mm");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));
		
		StringBuilder builder = new StringBuilder();
		builder.append("Número de Pedido: ");
		builder.append(getId());
		builder.append("\nInstante: ");
		builder.append(sdf.format(getInstant()));
		builder.append("\nCliente: ");
		builder.append(getClient().getName());
		builder.append("\nSituação do pagamento: ");
		builder.append(getPayment().getState().getDescription());
		builder.append("\nDetalhes:\n\n");
		
		for(OrderItem oi: getItems()) {
			builder.append(oi.toString());
		}
		
		builder.append("\nValor total: ");
		builder.append(nf.format(getTotal()));
		return builder.toString();
	}
}