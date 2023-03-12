package br.com.srg;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {
	
	//nome da fila
	private static String NAME_QUEUE = "WORK";
	
	public static void main(String[] args) throws Exception{
		
		//criar conexao
		//setar informacoes
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setUsername("admin");
		factory.setPassword("pass123");
		factory.setPort(5672);
		
		Connection connection = factory.newConnection();
		//System.out.println(connection.hashCode()); 
		
		//criar canal
		Channel channel = connection.createChannel();
		//System.out.println(channel);
		
		//declarar a fila que será utilizada
		//nome da fila, se é exclusiva, se é autodelete, se é duravel, map(args)
		channel.queueDeclare(NAME_QUEUE, false, false, false, null);
		
		
		//criar msg
		String message = "..........";
		
		//enviar msg
		channel.basicPublish("", NAME_QUEUE, null, message.getBytes());
		
		System.out.println("{x} Sent '"+message+"'");
	}
}
