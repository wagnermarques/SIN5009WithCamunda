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

	public static String usuarioDoEmail = "emailQueEnviaOEmailParaAgencia@dominio";
	public static String senhaDoEmail = "SenhaSecretaDoEmailQueEnviaOEmail";
	
	public static String usuarioDoPostgresql = "UsuarioDoPostgresqsl";
	public static String senhaDoPostgresql = "SenhaSecretaDoUsuarioDoPostgresql";
	
	
	
}
