package rdf;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;


public class Start {

	public static void main(String[] args) {
		String rdf= "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
		String xsd= "http://www.w3.org/2001/XMLSchema#";
		String mo= "http://purl.org/ontology/mo#";
		String rdfs= "http://www.w3.org/2000/01/rdf-schema#";
		String m= "http://www.kanzaki.com/ns/music#";
		String owl= "http://www.w3.org/2002/07/owl#";
		String dbp= "http://dbpedia.org/property#";
		String foaf= "http://xlmns.com/foaf/0.1#";
		String dbr = "http://dbpedia.org/resource#";
		String dbo= "http://dbpedia.org/ontology#";
		String partP="http://debpedia.org/property/part#";
		String partR="http://debpedia.org/resource/part#";
		
		
		Model model = ModelFactory.createDefaultModel();
        
        model.setNsPrefix("dbr",dbr);
        model.setNsPrefix("rdf",rdf);
        model.setNsPrefix("xsd",xsd);
        model.setNsPrefix("mo",mo);
        model.setNsPrefix("rdfs",rdfs);
        model.setNsPrefix("m",m);
        model.setNsPrefix("owl",owl);
        model.setNsPrefix("dbp",dbp);
        model.setNsPrefix("dbo",dbo);
        model.setNsPrefix("foaf",foaf);
        model.setNsPrefix("partP",partP);
        model.setNsPrefix("partR",partR);
        
        Resource Mozart = model.createResource(dbr+"Wolfgang_Amadeus_Mozart");
        Resource Jupyter = model.createResource(dbr+"Symphony_No._41_Mozart");
        Resource Symphony = model.createResource(m+"Symphony");
        Resource MusicComposer = model.createResource(dbo+"MusicComposer");
        Resource Claudio_Abbado = model.createResource(dbr+"Claudio_Abbado");
        Resource MusicDirector = model.createResource(dbo+"MusicDirector");
        Resource Person = model.createResource(foaf+"Person");
        Resource London_Symphony_Orchestra = model.createResource(dbr+"London_Symphony_Orchestra");
        Resource symphony_Orchestras = model.createResource(dbr+"symphony_Orchestras");
        Resource Orchestra = model.createResource(dbr+"Orchestra");
       
       
        
        Property subClassOf = model.createProperty(owl+"subClassOf");
        Property type = model.createProperty(rdf+"type");
        Property label = model.createProperty(rdfs+"label");
        Property name = model.createProperty(foaf+"name");
        Property composed = model.createProperty(dbp+"composed");
        Property composed_of = model.createProperty(dbp+"composed_of");
        Property recorded = model.createProperty(dbp+"recorded");
        Property recordedIn = model.createProperty(dbo+"recordedIn");
        Property recordedBy = model.createProperty(dbo+"recordedBy");
        Property recordedAt = model.createProperty(dbo+"recordedAt");
         
        
        
        model.add(Jupyter,label,"Jupyter")
        .add(Jupyter, type, Symphony)
        .add(MusicComposer, subClassOf, Person)
        .add(MusicDirector, subClassOf, Person)
        .add(Claudio_Abbado, type, MusicDirector)
        .add(Mozart, type, MusicComposer)
        .add(Mozart, name, "Wolfgang Amadeus Mozart")
        .add(Mozart, composed, Jupyter)
        .add(London_Symphony_Orchestra, label, "orchestre symphonique de londres")
        .add(Claudio_Abbado, recorded, Jupyter)
        .add(Claudio_Abbado, name, "Claudio Abbado")
        .add(Jupyter, recordedIn, model.createTypedLiteral(1980))
        .add(Jupyter, recordedBy, model.createTypedLiteral("Claudio Abbado"))
        .add(Jupyter, recordedAt, model.createTypedLiteral("orchestre symphonique de londres"))
        .add(Jupyter, composed_of, model.createTypedLiteral("Molto Allegro"))
        .add(Jupyter, composed_of, model.createTypedLiteral("Menuetto"))
        .add(Jupyter, composed_of, model.createTypedLiteral("Andate Cantabile"))
        .add(Jupyter, composed_of, model.createTypedLiteral("Allegro Vivace"))
        .add(London_Symphony_Orchestra, recorded, Jupyter)
        .add(symphony_Orchestras, subClassOf, Orchestra)
        .add(London_Symphony_Orchestra, type, symphony_Orchestras);
        
        
        
        
        model.write(System.out,"TURTLE");
        
	}
	
}

