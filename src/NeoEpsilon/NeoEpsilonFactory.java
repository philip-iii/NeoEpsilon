/**
 */
package NeoEpsilon;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see NeoEpsilon.NeoEpsilonPackage
 * @generated
 */
public interface NeoEpsilonFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NeoEpsilonFactory eINSTANCE = NeoEpsilon.impl.NeoEpsilonFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Shelf</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Shelf</em>'.
	 * @generated
	 */
	Shelf createShelf();

	/**
	 * Returns a new object of class '<em>Box</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Box</em>'.
	 * @generated
	 */
	Box createBox();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	NeoEpsilonPackage getNeoEpsilonPackage();

} //NeoEpsilonFactory
