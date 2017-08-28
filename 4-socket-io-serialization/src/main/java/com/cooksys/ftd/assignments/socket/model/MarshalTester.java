package com.cooksys.ftd.assignments.socket.model;

import java.io.File;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

//The purpose of this program is to create the student and config xml files to be used by the client/server application

public class MarshalTester {

	public static void main(String[] args) 
	{
		Scanner scanner = new Scanner(System.in);
		String input;
		
		System.out.print("[1] Create XML files\n[2] Test XML files\n::");
		
		input = scanner.nextLine();
		
		if(input.equals("1"))
			createXML();
		else if(input.equals("2"))
			testXML();
		
	}
	
	private static void createXML()		//Creates sample xml files 
	{
		JAXBContext jaxb;
		Marshaller marshaller;
	
		Student student;
		Config config;
		LocalConfig local;
		RemoteConfig remote;
		String studentLoc = "./config/student.xml";
		String configLoc = "./config/config.xml";
		
		student = new Student();
		student.setFirstName("Mark");
		student.setLastName("Dwight");
		student.setFavoriteLanguage("JAVA");
		student.setFavoriteIDE("Eclipse");
		student.setFavoriteParadigm("Agile");
		
		local = new LocalConfig();
		local.setPort(4455);
		
		remote = new RemoteConfig();
		remote.setHost("localhost");	//10.1.1.229 for Nick's
		remote.setPort(4455);
		
		config = new Config();
		config.setLocal(local);
		config.setRemote(remote);
		config.setStudentFilePath(studentLoc);
		
		try 
		{
			jaxb = JAXBContext.newInstance(Student.class,Config.class,RemoteConfig.class,LocalConfig.class);
			marshaller = jaxb.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			marshaller.marshal(student, new File(studentLoc));
			marshaller.marshal(config, new File(configLoc));
		} 	
		catch (JAXBException e) 
		{
			System.out.println("Tester could not create JAXB instance");
			e.printStackTrace();
		}
		
		System.out.println("XML files created");

	}
	
	private static void testXML()	//verifies that the existing xml files are in the proper format by unmarshalling them and printing the contents of the objects they contain
	{
		JAXBContext jaxb;
		Unmarshaller unmarshaller;
		Student student;
		Config config;
		LocalConfig local;
		RemoteConfig remote;
		String studentLoc = "./config/student.xml";
		String configLoc = "./config/config.xml";
		
		try 
		{
			jaxb = JAXBContext.newInstance(Student.class,Config.class,RemoteConfig.class,LocalConfig.class);
			unmarshaller = jaxb.createUnmarshaller();
			
			student = (Student)unmarshaller.unmarshal(new File(studentLoc));
			config = (Config)unmarshaller.unmarshal(new File(configLoc));
			
			System.out.println("Student Info: \n" + student);
			System.out.println("Config Info: \nStudent Filepath: "+config.getStudentFilePath());
			System.out.println("Local Host Port: "+config.getLocal().getPort());
			System.out.println("Remote Host Address and Port: "+config.getRemote().getHost()+" and "+config.getRemote().getPort());
		} 
		catch (JAXBException e) 
		{
			e.printStackTrace();
		}
	}
}
