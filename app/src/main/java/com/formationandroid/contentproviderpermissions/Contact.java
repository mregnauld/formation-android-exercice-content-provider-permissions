package com.formationandroid.contentproviderpermissions;

public class Contact
{
	
	// Attributs :
	private String nom;
	private String numero;
	
	
	/**
	 * Constructeur.
	 * @param nom Nom
	 * @param numero Numéro de téléphone
	 */
	Contact(String nom, String numero)
	{
		this.nom = nom;
		this.numero = numero;
	}
	
	/**
	 * Getter nom.
	 * @return Nom
	 */
	public String getNom()
	{
		return nom;
	}
	
	/**
	 * Getter numéro.
	 * @return Numéro
	 */
	public String getNumero()
	{
		return numero;
	}
	
}
