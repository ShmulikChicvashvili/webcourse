package com.technion.coolie.ug.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

import android.content.Context;

/**
 * Class for reading and writing to the disk
 * 
 * @author YouAreI
 * 
 */
public class SerializeIO {
	/**
	 * creates the file 'filename' in case it doesn't exist.
	 * 
	 * @param context
	 * @param filename
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws StreamCorruptedException
	 */
	public static void createFile(final Context context, final String filename,
			final Serializable initial) throws StreamCorruptedException,
			IOException, ClassNotFoundException {
		try {
			load(context, filename); // just TRY to load
			return;
		} catch (final EOFException e) {
			//
		} catch (final FileNotFoundException e) {
			//
		}

		save(context, filename, initial);
	}

	/**
	 * Save data
	 * 
	 * @param context
	 *            - currently running activity
	 * @param filename
	 *            - name for the data. can be a new file, just keep it final
	 * @param data
	 *            - the actual data to write
	 * @throws IOException
	 *             for write error
	 */
	public static void save(final Context context, final String filename,
			final Serializable data) throws IOException {
		final FileOutputStream fos = context.openFileOutput(filename,
				Context.MODE_PRIVATE);
		final OutputStream buffer = new BufferedOutputStream(fos);
		final ObjectOutput output = new ObjectOutputStream(buffer);
		output.writeObject(data);
		output.close();
	}

	/**
	 * Load data
	 * 
	 * @param context
	 *            - currently running activity
	 * @param filename
	 *            - name for the data. can be a new file, just keep it final
	 * @return the data from the file
	 * @throws StreamCorruptedException
	 *             for bad filename
	 * @throws IOException
	 *             for read error
	 * @throws ClassNotFoundException
	 *             data read was not Serializable
	 */
	public static Serializable load(final Context context, final String filename)
			throws StreamCorruptedException, IOException,
			ClassNotFoundException {
		final FileInputStream fis = context.openFileInput(filename);
		final InputStream buffer = new BufferedInputStream(fis);
		final ObjectInput input = new ObjectInputStream(buffer);
		final Serializable data = (Serializable) input.readObject();
		input.close();
		return data;
	}

}
