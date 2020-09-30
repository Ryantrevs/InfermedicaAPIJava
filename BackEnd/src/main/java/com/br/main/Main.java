package com.br.main;

import java.util.Scanner;

import com.br.Entity.Enviar.diagnostico.Evidence;
import com.br.Entity.Enviar.diagnostico.FinalDiagnostico;
import com.br.Entity.Enviar.diagnostico.Output;
import com.br.Entity.Enviar.diagnostico.Req;
import com.br.Entity.receber.parse.Parse;
import com.br.requests.defineRequest;

public class Main {
	
	public static void main(final String[] args) {
		
		String case_id = "kasdjkasd";
		int position = 0;
		int i;
		
		Scanner lerString = new Scanner(System.in);
		Scanner lerInt = new Scanner(System.in);
		
		String symptom;
		
		defineRequest conversation = new defineRequest();
		System.out.println("O que você está sentindo?");
		symptom = lerString.nextLine();
		System.out.println("digite seu sexo:");
		String sex = lerString.nextLine();
		System.out.println("digite sua idade:");
		int age = lerInt.nextInt();
		
		Parse parse = conversation.parse(symptom, case_id,position);
		
		System.out.println("Tem mais algum sintoma?");
		String sintoma = lerString.nextLine();
		
		
		while((!sintoma.equalsIgnoreCase(""))&&(!sintoma.equalsIgnoreCase("no"))) {
			
			position++;
			conversation.addSintoma(sintoma, parse, case_id, position);
			
			
			System.out.println("Tem mais algum sintoma?");
			sintoma = lerString.nextLine();
		}
		Output diag = conversation.diagnostico(parse, case_id, position, sex, age);
		FinalDiagnostico diagnostico = new FinalDiagnostico();
		diagnostico.age = age;
		diagnostico.sex = sex;
		for(int j=0;j<parse.mentions.size();j++) {
			Evidence evidence = new Evidence();
			evidence.choice_id = parse.mentions.get(j).choice_id;
			evidence.id = parse.mentions.get(j).id;
			diagnostico.evidence.add(j,evidence);
		}
		while((!diag.question.text.equalsIgnoreCase(""))) {
			System.out.println(diag.question.text);
			System.out.println("Opções, escolha um numero:");
			if(diag.question.items.size()>1) {
				for(i=0;i<diag.question.items.size();i++) {
					System.out.println(diag.question.items.get(i).name+" = "+(i+1));
				}
				int resp = lerInt.nextInt();
				diag = conversation.diagnosticoFinal(diag,position, case_id, resp,null,diagnostico);
			}else {
				for(i=0;i<diag.question.items.get(0).choices.size();i++) {
					System.out.println(diag.question.items.get(0).choices.get(i).label);
				}
				int resp = lerInt.nextInt();
				diag = conversation.diagnosticoFinal(diag,position, case_id, resp,"item unico",diagnostico);
			}
			position++;
		}
	}
}
	

