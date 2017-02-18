/**
 */
package NeoEpsilon.impl;

import NeoEpsilon.Box;
import NeoEpsilon.NeoEpsilonPackage;
import NeoEpsilon.Shelf;

import fr.inria.atlanmod.neoemf.core.DefaultPersistentEObject;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Shelf</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link NeoEpsilon.impl.ShelfImpl#getName <em>Name</em>}</li>
 *   <li>{@link NeoEpsilon.impl.ShelfImpl#getBoxes <em>Boxes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ShelfImpl extends DefaultPersistentEObject implements Shelf {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ShelfImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NeoEpsilonPackage.Literals.SHELF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eGet(NeoEpsilonPackage.Literals.SHELF__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(NeoEpsilonPackage.Literals.SHELF__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Box> getBoxes() {
		return (EList<Box>)eGet(NeoEpsilonPackage.Literals.SHELF__BOXES, true);
	}

} //ShelfImpl
