package simpledb;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Tuple maintains information about the contents of a tuple. Tuples have a
 * specified schema specified by a TupleDesc object and contain Field objects
 * with the data for each field.
 */
public class Tuple implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private TupleDesc td;
    private Field[] fields;
    private RecordId recordId;
    /**
     * Create a new tuple with the specified schema (type).
     * 
     * @param td
     *            the schema of this tuple. It must be a valid TupleDesc
     *            instance with at least one field.
     */
    public Tuple(TupleDesc td) {
        // some code goes here
    	this.td = td;
    	this.fields = new Field[td.numFields()];
    }

    /**
     * @return The TupleDesc representing the schema of this tuple.
     */
    public TupleDesc getTupleDesc() {
        // some code goes here
        return this.td;
    }

    /**
     * @return The RecordId representing the location of this tuple on disk. May
     *         be null.
     */
    public RecordId getRecordId() {
        // some code goes here
        return this.recordId;
    }

    /**
     * Set the RecordId information for this tuple.
     * 
     * @param rid
     *            the new RecordId for this tuple.
     */
    public void setRecordId(RecordId rid) {
        this.recordId = rid;
    }

    /**
     * Change the value of the ith field of this tuple.
     * 
     * @param i
     *            index of the field to change. It must be a valid index.
     * @param f
     *            new value for the field.
     */
    public void setField(int i, Field f) {
    	if(i < 0 || i >= this.fields.length) {
    		throw new NoSuchElementException("Index " + i + " out of range: 0~" + this.fields.length);
    	}
        this.fields[i] = f;
    }

    /**
     * @return the value of the ith field, or null if it has not been set.
     * 
     * @param i
     *            field index to return. Must be a valid index.
     */
    public Field getField(int i) {
    	if(i < 0 || i >= this.fields.length) {
    		throw new NoSuchElementException("Index " + i + " out of range: 0~" + this.fields.length);
    	}
        return this.fields[i];
    }

    /**
     * Returns the contents of this Tuple as a string. Note that to pass the
     * system tests, the format needs to be as follows:
     * 
     * column1\tcolumn2\tcolumn3\t...\tcolumnN\n
     * 
     * where \t is any whitespace, except newline, and \n is a newline
     */
    public String toString() {
        String str = "";
        for(int i = 0; i < this.td.numFields(); i++) {
        	str += this.td.getFieldName(i);
        	if(i != this.td.numFields() - 1) {
        		str += " ";
        	} else {
        		// new line here
        	}
        }
        return str;
    }
    
    /**
     * @return
     *        An iterator which iterates over all the fields of this tuple
     * */
    public Iterator<Field> fields()
    {
    	return new Iterator<Field>() {
        	private int idx = -1;
        	
			@Override
			public boolean hasNext() {
				return idx+1< fields.length;
			}
			@Override
			public Field next() {
				if (++idx == fields.length) {
					throw new NoSuchElementException();
				} else {
					return fields[idx];
				}
			}
			@Override
			public void remove() {
				throw new UnsupportedOperationException("unimplemented");
			}
        };
    }
    
    /**
     * reset the TupleDesc of thi tuple
     * */
    public void resetTupleDesc(TupleDesc td)
    {
        // some code goes here
    }
}
