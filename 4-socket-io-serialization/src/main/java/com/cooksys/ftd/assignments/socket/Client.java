package com.cooksys.ftd.assignments.socket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.LocalConfig;
import com.cooksys.ftd.assignments.socket.model.RemoteConfig;
import com.cooksys.ftd.assignments.socket.model.Student;

public class Client {

    /**
     * The client should load a {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
     * <project-root>/config/config.xml path, using the "port" and "host" properties of the embedded
     * {@link com.cooksys.ftd.assignments.socket.model.RemoteConfig} object to create a socket that connects to
     * a {@link Server} listening on the given host and port.
     *
     * The client should expect the server to send a {@link com.cooksys.ftd.assignments.socket.model.Student} object
     * over the socket as xml, and should unmarshal that object before printing its details to the console.
     */
    public static void main(String[] args) {

    	String configLoc = "./config/config.xml";
    	File configFile = new File(configLoc);

    	
    	Config config;
    	RemoteConfig remote;
    	InputStream input;
    	Socket socket;
    	Student student;
    	JAXBContext jaxbContext;
    	Unmarshaller unmarshaller;
    	   	  
    	
    	try
    	{	
    	//Unmarshall config details from config xml file
    		jaxbContext = JAXBContext.newInstance(Config.class,Student.class,RemoteConfig.class,LocalConfig.class);
    		unmarshaller = jaxbContext.createUnmarshaller();
    		config = (Config)unmarshaller.unmarshal(configFile);
    		
    	//Get details from the remoteconfig object contained in the config.xml
    		remote = config.getRemote();
    		
    	//Setup socket and the input/output streams
    		socket = new Socket(remote.getHost(),remote.getPort());
    		input = socket.getInputStream();
    		
    	//receive student xml from server   		
    	//Unmarshall student from xml and print out its info
    		student = (Student)unmarshaller.unmarshal(input);
    		System.out.println(student);
    		
    	//Close the connection
    		socket.close();
    	}
    	catch(JAXBException e)
    	{
    		System.out.println(e);
    	}
    	catch(FileNotFoundException e)
    	{
    		System.out.println(e);
    	}
    	catch(IOException e)
    	{
    		System.out.println(e);
    	}
    	  	
    }
}
