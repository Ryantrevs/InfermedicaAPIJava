package com.br.requests;

import java.util.ArrayList;
import java.util.List;

import com.br.API.AcessAPI;
import com.br.Entity.Enviar.diagnostico.Diagnostico;
import com.br.Entity.Enviar.diagnostico.Evidence;
import com.br.Entity.Enviar.diagnostico.FinalDiagnostico;
import com.br.Entity.Enviar.diagnostico.Output;
import com.br.Entity.receber.parse.Mentions;
import com.br.Entity.receber.parse.Parse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class defineRequest {
	
	AcessAPI api = new AcessAPI();
	Gson gson = new Gson();
	
	public Parse parse(String request,String case_id,int position) {
		
		String json = "{\r\n    \"text\": \r\n    \""+request+"\"\r\n}";
		
		String result = api.endpoint("parse", json, case_id);
		Gson gson = new Gson();
		Parse parse = gson.fromJson(result, Parse.class);
		
		
		return parse;
	}
	
	public void addSintoma(String sintoma,Parse parse,String case_id,int position) {
		
		String json = "{\r\n    \"text\": \r\n    \""+sintoma+"\"\r\n}";
		
		String result = api.endpoint("parse", json, case_id);
		
		Parse temp = new Parse();
		
		Gson gson = new Gson();	
		temp = gson.fromJson(result,Parse.class);
		Mentions mentions = new Mentions(
				temp.mentions.get(0).id,
				temp.mentions.get(0).name,
				temp.mentions.get(0).common_name,
				temp.mentions.get(0).orth,
				temp.mentions.get(0).choice_id,
				temp.mentions.get(0).type);
		
		parse.mentions.add(position, mentions);
		
	}
	public Output diagnostico(Parse parse,String case_id,int position,String sexo,int idade) {
	
		Diagnostico enviar = new Diagnostico();
		
		enviar.age = idade;
		enviar.sex = sexo;
		for(int i=0;i<=position;i++) {
			Evidence evidence = new Evidence();
			evidence.choice_id = parse.mentions.get(i).choice_id;
			evidence.id = parse.mentions.get(i).id;
			
			enviar.evidence.add(i,evidence);
		}
				
		
		String json = gson.toJson(enviar);
		
		String result = api.endpoint("diagnosis", json, case_id);
		
		Output resultado = new Output();
		
		resultado = gson.fromJson(result, Output.class);
		
		return resultado;
		
	}
	
	
	public Output diagnosticoFinal(Output saida,int position,String case_id,
			int resp,String option,FinalDiagnostico diagnostico) {
		
		String result = "";
		if(option == null) {
			Evidence evidence = new Evidence();
			evidence.choice_id = saida.question.items.get(resp-1).choices.get(0).id;
			evidence.id = saida.question.items.get(resp-1).id;
			diagnostico.evidence.add(position+1,evidence);
			String json = gson.toJson(diagnostico);
			
			result = api.endpoint("diagnosis", json, case_id);
		}else {
			System.out.println(saida.question.items.get(0).choices.get(resp-1).id);
			Evidence evidence = new Evidence();
			evidence.choice_id = saida.question.items.get(0).choices.get(resp-1).id;
			evidence.id = saida.question.items.get(0).id;
			diagnostico.evidence.add(position+1,evidence);
			String json = gson.toJson(diagnostico);
			
			result = api.endpoint("diagnosis", json, case_id);
		}
		
		System.out.println(result);
		
		Output resultado = new Output();
		
		resultado = gson.fromJson(result, Output.class);
		
		return resultado;
		
		
	}
}
