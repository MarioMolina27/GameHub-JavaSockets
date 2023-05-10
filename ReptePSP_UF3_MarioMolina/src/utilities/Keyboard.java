package utilities;// =============================================================================
// Classe utilities.Keyboard

// Paquet


// Imports
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Conté diferents mètodes per llegir dades des del teclat, en funció del tipus 
 * de dada a llegir. Versió amb excepcions.
 * Data de creació: 16/04/2021
 * @author José Luis García Mañas
 */
public class Keyboard
{
	/**
	 * Llegeix una cadena des del teclat i la retorna.
	 * @return La cadena llegida des del teclat
	 */
	public static String readString()
	{
		return new Scanner(System.in).nextLine();
	}

	/**
	 * Llegeix un caràcter des del teclat i el retorna.
	 * @return El caràcter llegit des del teclat
	 * @throws InputMismatchException Quan s'introdueix més d'un caràcter
	 */
	public static char readChar() throws InputMismatchException
	{
		// Inicialitzem el caràcter a caràcter buit. Si es produeix algun error,
		// es retornarà aquest valor
		char c = 0;

		// Si no es produeix cap error, es retornarà el caràcter llegit
		String s = readString();

		// Controlem que el que s'ha llegit és un caràcter
		if(s.length() == 1)
		{
			c = s.charAt(0);
		}
		else
		{
			throw new InputMismatchException();
		}

		return c;
	}

	/**
	 * Llegeix un número enter (short) des del teclat i el retorna.
	 * @return El número enter (short) llegit des del teclat
	 * @throws InputMismatchException Quan s'introdueixen caràcters no numèrics
	 *		   o bé excedeixen la capacitat d'un short
	 */
	public static short readShort() throws InputMismatchException
	{
		return new Scanner(System.in).nextShort();
	}

	/**
	 * Llegeix un número enter des del teclat i el retorna.
	 * @return El número enter llegit des del teclat
	 * @throws InputMismatchException Quan s'introdueixen caràcters no numèrics
	 *		   o bé excedeixen la capacitat d'un int
	 */
	public static int readInt() throws InputMismatchException
	{
		return new Scanner(System.in).nextInt();
	}

	/**
	 * Llegeix un número enter (long) des del teclat i el retorna.
	 * @return El número enter (long) llegit des del teclat
	 * @throws InputMismatchException Quan s'introdueixen caràcters no numèrics
	 *		   o bé excedeixen la capacitat d'un long
	 */
	public static long readLong() throws InputMismatchException
	{
		return new Scanner(System.in).nextLong();
	}

	/**
	 * Llegeix un número enter (BigInteger) des del teclat i el retorna.
	 * @return El número enter (BigInteger) llegit des del teclat
	 * @throws InputMismatchException Quan s'introdueixen caràcters no numèrics
	 */
	public static BigInteger readBigInteger() throws InputMismatchException
	{
		return new Scanner(System.in).nextBigInteger();
	}

	/**
	 * Llegeix un número real (float) des del teclat i el retorna. 
	 * @return El número real (float) llegit des del teclat
	 * @throws InputMismatchException Quan s'introdueixen caràcters no numèrics
	 *		   o bé excedeixen la capacitat d'un float
	 */
	public static float readFloat() throws InputMismatchException
	{
		return new Scanner(System.in).nextFloat();
	}

	/**
	 * Llegeix un número real (double) des del teclat i el retorna. 
	 * @return El número real (double) llegit des del teclat
	 * @throws InputMismatchException Quan s'introdueixen caràcters no numèrics
	 *		   o bé excedeixen la capacitat d'un double
	 */
	public static double readDouble() throws InputMismatchException
	{
		return new Scanner(System.in).nextDouble();
	}

	/**
	 * Llegeix un número real (BigDecimal) des del teclat i el retorna. 
	 * @return El número real (BigDecimal) llegit des del teclat
	 * @throws InputMismatchException Quan s'introdueixen caràcters no numèrics
	 */
	public static BigDecimal readBigDecimal() throws InputMismatchException
	{
		return new Scanner(System.in).nextBigDecimal();
	}

	/**
	 * Llegeix un valor booleà des del teclat i el retorna. 
	 * @return El valor booleà llegit des del teclat (true o false)
	 * @throws InputMismatchException Quan s'introdueixen valors no adequats per
	 *		   a un boolean
	 */
	public static boolean readBoolean() throws InputMismatchException
	{
		return new Scanner(System.in).nextBoolean();
	}

	/**
	 * Mostra un missatge a l'usuari per què aquest introdueixi una cadena des
	 * del teclat.
	 * @param message Missatge a mostrar a l'usuari
	 * @return La cadena llegida des del teclat
	 */
	public static String getString(String message)
	{
		System.out.print(message + ": ");
		return readString();
	}

	/**
	 * Mostra un missatge a l'usuari per què aquest introdueixi un caràcter des
	 * del teclat.
	 * @param message Missatge a mostrar a l'usuari
	 * @return El caràcter llegida des del teclat
	 * @throws InputMismatchException Quan s'introdueix més d'un caràcter	 */
	public static char getChar(String message) throws InputMismatchException
	{
		System.out.print(message + ": ");
		return readChar();
	}

	/**
	 * Mostra un missatge a l'usuari per què aquest introdueixi un enter (short) 
	 * des del teclat.
	 * Llegeix un número enter (short) des del teclat i el retorna.
	 * @param message Missatge a mostrar a l'usuari
	 * @return El número enter (short) llegit des del teclat
	 * @throws InputMismatchException Quan s'introdueixen caràcters no numèrics
	 *		   o bé excedeixen la capacitat d'un short
	 */
	public static short getShort(String message) throws InputMismatchException
	{
		System.out.print(message + ": ");
		return readShort();
	}

	/**
	 * Mostra un missatge a l'usuari per què aquest introdueixi un enter (int) 
	 * des del teclat.
	 * Llegeix un número enter (int) des del teclat i el retorna.
	 * @param message Missatge a mostrar a l'usuari
	 * @return El número enter (int) llegit des del teclat
	 * @throws InputMismatchException Quan s'introdueixen caràcters no numèrics
	 *		   o bé excedeixen la capacitat d'un int
	 */
	public static int getInt(String message) throws InputMismatchException
	{
		System.out.print(message + ": ");
		return readInt();
	}

	/**
	 * Mostra un missatge a l'usuari per què aquest introdueixi un enter (long) 
	 * des del teclat.
	 * Llegeix un número enter (long) des del teclat i el retorna.
	 * @param message Missatge a mostrar a l'usuari
	 * @return El número enter (long) llegit des del teclat
	 * @throws InputMismatchException Quan s'introdueixen caràcters no numèrics
	 *		   o bé excedeixen la capacitat d'un long
	 */
	public static long getLong(String message) throws InputMismatchException
	{
		System.out.print(message + ": ");
		return readLong();
	}

	/**
	 * Mostra un missatge a l'usuari per què aquest introdueixi un enter 
	 * (BigInteger) des del teclat.
	 * Llegeix un número enter (BigInteger) des del teclat i el retorna.
	 * @param message Missatge a mostrar a l'usuari
	 * @return El número enter (BigInteger) llegit des del teclat
	 * @throws InputMismatchException Quan s'introdueixen caràcters no numèrics
	 */
	public static BigInteger getBigInteger(String message)
			throws InputMismatchException
	{
		System.out.print(message + ": ");
		return readBigInteger();
	}

	/**
	 * Mostra un missatge a l'usuari per què aquest introdueixi un real (float) 
	 * des del teclat.
	 * Llegeix un número un real (float) des del teclat i el retorna.
	 * @param message Missatge a mostrar a l'usuari
	 * @return El número un real (float) llegit des del teclat
	 * @throws InputMismatchException Quan s'introdueixen caràcters no numèrics
	 *		   o bé excedeixen la capacitat d'un float
	 */
	public static float getFloat(String message) throws InputMismatchException
	{
		System.out.print(message + ": ");
		return readFloat();
	}

	/**
	 * Mostra un missatge a l'usuari per què aquest introdueixi un real (double) 
	 * des del teclat.
	 * Llegeix un número un real (double) des del teclat i el retorna.
	 * @param message Missatge a mostrar a l'usuari
	 * @return El número un real (double) llegit des del teclat
	 * @throws InputMismatchException Quan s'introdueixen caràcters no numèrics
	 *		   o bé excedeixen la capacitat d'un double
	 */
	public static double getDouble(String message) throws InputMismatchException
	{
		System.out.print(message + ": ");
		return readDouble();
	}

	/**
	 * Mostra un missatge a l'usuari per què aquest introdueixi un real 
	 * (BigDecimal) des del teclat.
	 * Llegeix un número un real (BigDecimal) des del teclat i el retorna.
	 * @param message Missatge a mostrar a l'usuari
	 * @return El número un real (BigDecimal) llegit des del teclat
	 * @throws InputMismatchException Quan s'introdueixen caràcters no numèrics
	 */
	public static BigDecimal getBigDecimal(String message)
	{
		System.out.print(message + ": ");
		return readBigDecimal();
	}

	/**
	 * Mostra un missatge a l'usuari per què aquest introdueixi un valor booleà 
	 * des del teclat.
	 * @param message Missatge a mostrar a l'usuari
	 * @return El número un valor boolea llegit des del teclat
	 * @throws InputMismatchException Quan s'introdueixen valors no adequats per
	 *		   a un boolean
	 */
	public static boolean getBoolean(String message) throws InputMismatchException
	{
		System.out.print(message + ": ");
		return readBoolean();
	}
}
// =============================================================================
