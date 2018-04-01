/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package openFire.security.monitoring.bindings;

public class PrivateKey extends Blob {
  private transient long swigCPtr;

  protected PrivateKey(long cPtr, boolean cMemoryOwn) {
    super(irohaJNI.PrivateKey_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(PrivateKey obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        irohaJNI.delete_PrivateKey(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public PrivateKey(String private_key) {
    this(irohaJNI.new_PrivateKey__SWIG_0(private_key), true);
  }

  public PrivateKey(Blob blob) {
    this(irohaJNI.new_PrivateKey__SWIG_1(Blob.getCPtr(blob), blob), true);
  }

  public String toString() {
    return irohaJNI.PrivateKey_toString(swigCPtr, this);
  }

}
