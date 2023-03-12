package br.com.srg;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Receiver {
	// nome da fila
	private static String NAME_QUEUE = "WORK";
	
	private static void doWork(String task) throws InterruptedException{
		for(char ch:task.toCharArray()) {
			if(ch == '.') Thread.sleep(10000);
		}
	}

	public static void main(String[] args) throws Exception {

		// criar conexao
		// setar informacoes
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setUsername("admin");
		factory.setPassword("pass123");
		factory.setPort(5672);

		Connection connection = factory.newConnection();
		// System.out.println(connection.hashCode());

		// criar canal
		Channel channel = connection.createChannel();
		// System.out.println(channel);

		// declarar a fila que será utilizada
		// nome da fila, se é exclusiva, se é autodelete, se é duravel, map(args)
		channel.queueDeclare(NAME_QUEUE, false, false, false, null);
		
		DeliverCallback deliveryCallback = (ConsumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println("{x}Received message: '" +message+"'");
			
			try {
				doWork(message);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
				System.out.println("{x} Done");
			}
		};
		Boolean autoack = true;
		channel.basicConsume(NAME_QUEUE, autoack, deliveryCallback, ConsumerTag->{});

		
	}
}
