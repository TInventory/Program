package edu.mtu.tinventory.database.query.queries;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

/**
 * 
 * @author James Helm
 * @since 10/12/2017
 * 
 *        Used as a general form of wrapper for byte encoded array streams
 */
public class InfoStreams extends DataOutputStream{

    /**
     * Stream of an encoded byte array
     */
    private final ByteArrayOutputStream outputStream;

    /**
     * 
     * @param outStream Output stream that contains encoded bytes
     */
    public InfoStreams(ByteArrayOutputStream outStream)
    {
        // pass up to DataOutputStream
        super(outStream);
        // set to class variable
        outputStream = outStream;
    }

    /**
     * Getter for byte array
     * 
     * @return byte[] Return an array of bytes from stream
     */
    public byte[] getByteArray() {
        // Converts byte stream to byte array and returns it
        return outputStream.toByteArray();
    }
}
