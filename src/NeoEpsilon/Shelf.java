/**
 */
package NeoEpsilon;

import fr.inria.atlanmod.neoemf.core.PersistentEObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Shelf</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link NeoEpsilon.Shelf#getName <em>Name</em>}</li>
 *   <li>{@link NeoEpsilon.Shelf#getBoxes <em>Boxes</em>}</li>
 * </ul>
 *
 * @see NeoEpsilon.NeoEpsilonPackage#getShelf()
 * @model
 * @extends PersistentEObject
 * @generated
 */
public interface Shelf extends PersistentEObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see NeoEpsilon.NeoEpsilonPackage#getShelf_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link NeoEpsilon.Shelf#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Boxes</b></em>' containment reference list.
	 * The list contents are of type {@link NeoEpsilon.Box}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Boxes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Boxes</em>' containment reference list.
	 * @see NeoEpsilon.NeoEpsilonPackage#getShelf_Boxes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Box> getBoxes();

} // Shelf
