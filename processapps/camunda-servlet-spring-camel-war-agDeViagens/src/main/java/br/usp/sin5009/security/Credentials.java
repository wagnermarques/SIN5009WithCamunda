package br.usp.sin5009.security;

/*
 * Essa classe he um antipatter
 * mas fizemos por questao de tempo, apenas para efeitos da apresentacao
 * deste trabalho especifico
 * Depois esperamos saldar este debito tecnico
 * 
 * Precisavamos de um ponto unico para credenciais pra que elas nao ficassem espalhadas
 * no codigo e pudessemos nao comita-las
 * 
 */

public class Credentials {

	public static String usuarioDoEmail = "email@que.enviara.emails";
	public static String senhaDoEmail = "senhaDoEmailQueEnvia";
	
	public static String usuarioDoPostgresql = "usuarioDoPostgresql";
	public static String senhaDoPostgresql = "senhaDoPostgresql";
	
}
