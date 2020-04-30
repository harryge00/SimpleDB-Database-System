package simpledb;

import java.io.Serializable;
import java.util.*;

/**
 * TupleDesc describes the schema of a tuple.
 */
public class TupleDesc implements Serializable {

    /**
     * A help class to facilitate organizing the information of each field
     * */
    public static class TDItem implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * The type of the field
         * */
        public final Type fieldType;
        
        /**
         * The name of the field
         * */
        public final String fieldName;

        public TDItem(Type t, String n) {
            this.fieldName = n;
            this.fieldType = t;
        }

        public String toString() {
            return fieldName + "(" + fieldType + ")";
        }
    }

    /**
     * @return
     *        An iterator which iterates over all the field TDItems
     *        that are included in this TupleDesc
     * */
    public Iterator<TDItem> iterator() {
        // some code goes here
        return this.tdItemList.iterator();
    }

    private static final long serialVersionUID = 1L;
    
    private List<TDItem> tdItemList;
    /**
     * Create a new TupleDesc with typeAr.length fields with fields of the
     * specified types, with associated named fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     * @param fieldAr
     *            array specifying the names of the fields. Note that names may
     *            be null.
     */
    public TupleDesc(Type[] typeAr, String[] fieldAr) {
    	this.tdItemList = new ArrayList<TDItem>(typeAr.length);
    	for(int i = 0; i < typeAr.length; i++) {
    		this.tdItemList.add(new TDItem(typeAr[i], fieldAr[i]));
    	}
    }

    /**
     * Constructor. Create a new tuple desc with typeAr.length fields with
     * fields of the specified types, with anonymous (unnamed) fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     */
    public TupleDesc(Type[] typeAr) {
    	this.tdItemList = new ArrayList<TDItem>(typeAr.length);
    	for(int i = 0; i < typeAr.length; i++) {
    		this.tdItemList.add(new TDItem(typeAr[i], ""));
    	}
    }

    /**
     * @return the number of fields in this TupleDesc
     */
    public int numFields() {
        // some code goes here
        return this.tdItemList.size();
    }

    /**
     * Gets the (possibly null) field name of the ith field of this TupleDesc.
     * 
     * @param i
     *            index of the field name to return. It must be a valid index.
     * @return the name of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public String getFieldName(int i) throws NoSuchElementException {
        // some code goes here
    	if(i < 0 || i >= this.tdItemList.size()) {
    		throw new NoSuchElementException("Index out of range");
    	}
        return this.tdItemList.get(i).fieldName;
    }

    /**
     * Gets the type of the ith field of this TupleDesc.
     * 
     * @param i
     *            The index of the field to get the type of. It must be a valid
     *            index.
     * @return the type of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public Type getFieldType(int i) throws NoSuchElementException {
    	if(i < 0 || i >= this.tdItemList.size()) {
    		throw new NoSuchElementException("Index " + i + " out of range: 0~" + this.tdItemList.size());
    	}
        return this.tdItemList.get(i).fieldType;
    }

    /**
     * Find the index of the field with a given name.
     * 
     * @param name
     *            name of the field.
     * @return the index of the field that is first to have the given name.
     * @throws NoSuchElementException
     *             if no field with a matching name is found.
     */
	public int fieldNameToIndex(String name) throws NoSuchElementException {
        for(int i = 0; i < this.tdItemList.size(); i++) {
        	if(this.tdItemList.get(i).fieldName.equals(name)) {
        		return i;
        	}
        }
        throw new NoSuchElementException("No such field name " + name);
    }

    /**
     * @return The size (in bytes) of tuples corresponding to this TupleDesc.
     *         Note that tuples from a given TupleDesc are of a fixed size.
     */
    public int getSize() {
        // some code goes here
    	int size = 0;
    	for(int i = 0; i < this.tdItemList.size(); i++) {
    		Type tp = this.tdItemList.get(i).fieldType;
    		if(tp == Type.INT_TYPE) {
    			size += Type.INT_TYPE.getLen();
    		} else if(tp == Type.STRING_TYPE) {
    			size += Type.STRING_TYPE.getLen();
    		} else {
    			// Not possible by now.
    		}
    	}
        return size;
    }

    /**
     * Merge two TupleDescs into one, with td1.numFields + td2.numFields fields,
     * with the first td1.numFields coming from td1 and the remaining from td2.
     * 
     * @param td1
     *            The TupleDesc with the first fields of the new TupleDesc
     * @param td2
     *            The TupleDesc with the last fields of the TupleDesc
     * @return the new TupleDesc
     */
    public static TupleDesc merge(TupleDesc td1, TupleDesc td2) {
    	Type[] typeAr = new Type[td1.numFields() + td2.numFields()];
    	String[] stringAr = new String[td1.numFields() + td2.numFields()];
    	int i = 0;
        for(; i < td1.numFields(); i++) {
        	typeAr[i] = td1.getFieldType(i);
        	stringAr[i] = td1.getFieldName(i);
        }
        for(int j = 0; j < td2.numFields(); j++, i++) {
        	typeAr[i] = td2.getFieldType(j);
        	stringAr[i] = td2.getFieldName(j);
        }
        return new TupleDesc(typeAr, stringAr);
    }

    /**
     * Compares the specified object with this TupleDesc for equality. Two
     * TupleDescs are considered equal if they are the same size and if the n-th
     * type in this TupleDesc is equal to the n-th type in td.
     * 
     * @param o
     *            the Object to be compared for equality with this TupleDesc.
     * @return true if the object is equal to this TupleDesc.
     */
    public boolean equals(Object o) {
    	if (o == null)
    	      return false;
	    if (o instanceof TupleDesc) {
	    	TupleDesc that = (TupleDesc) o;
	    	if(that.numFields() != this.numFields()) {
	    		return false;
	    	}
	    	for(int i = 0; i < that.numFields(); i++) {
	    		if(that.getFieldName(i) != this.getFieldName(i) || that.getFieldType(i) != that.getFieldType(i)) {
	    			return false;
	    		}
	    	}
	    	return true;
	    }
        return false;
    }

    public int hashCode() {
        // If you want to use TupleDesc as keys for HashMap, implement this so
        // that equal objects have equals hashCode() results
        throw new UnsupportedOperationException("unimplemented");
    }

    /**
     * Returns a String describing this descriptor. It should be of the form
     * "fieldType[0](fieldName[0]), ..., fieldType[M](fieldName[M])", although
     * the exact format does not matter.
     * 
     * @return String describing this descriptor.
     */
    public String toString() {
        // some code goes here
    	String str = "";
    	for(int i = 0; i < this.tdItemList.size(); i++) {
    		TDItem td = this.tdItemList.get(i);
    		if(td.fieldType == Type.INT_TYPE) {
    			str += "INT_TYPE(" + td.fieldName + ")";
    		} else if(td.fieldType == Type.STRING_TYPE) {
    			str += "STRING_TYPE(" + td.fieldName + ")";
    		} else {
    			// Not possible by now.
    		}
    	}
        return str;
    }
}
