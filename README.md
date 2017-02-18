# NeoEpsilon
Isolated examples of integrating NeoEMF and Epsilon

## Contents

The isolated examples contain all the ingredients to run, including:

* a basic Ecore meta-model `NeoEpsilon.ecore` describing `Shelves` containing `Boxes` for illustrative purposes, corresponding NeoEMF-enabled generator model `NeoEpsilon.genmodel`; 
* an Epsilon Object Language (EOL) script `epsilon/neo.eol` to do some basic manipulations, such as creating a `Shelf` and adding a `Box` called "EpsilonBox";
* a Java class `app.App` for automating the processing of models by means of EOL, the Epsilon EMF API, and the Java EMF API for comparison.  

## Instructions

Run the `app.App` class. The `process` method has three modes of operation: `epsilon`, `api`, and `head2head`. It can operate on different types of resources (for the purposes of the example only support for `xmi` and `neo` resources is provided). If the resource type is `neo` it will also convert it to `xmi` by copying the contents of the resource in a separate `xmi` resource for comparison and further examination. Before creating a new resource of the corresponding type, it removes any existing resources with the same name.

The `process` method is called 6 times, executing all three modes of operation for both types of resources. The results are stored in the `instances` directory, in a corresponding sub-directory according to the mode.

* The first mode (`epsilon`) is using the `neo.eol` EOL script to manipulate the resource. The result is that when this mode is executed on a NeoEMF resource, only the top-level objects are properly created and stored. Any contained objects are not stored properly. On an XMI resource, everything works as expected. 

* The second mode (`api`) is using both the Java and Epsilon EMF APIs for comparison. The use of the Epsilon EMF API should be equivalent to the use of an Epsilon script. After creating a `Shelf` and a `Box` named "JavaBox" contained in it with the Java EMF API, another `Box` named "EpsilonBox" is created and added to the same `Shelf` by means of the Epsilon EMF API. Finally, another `Shelf` is added by means of the Epsilon EMF API for completeness. The elements of the model are printed out for quick feedback. The result is that when this mode is executed on a NeoEMF resource and the Epsilon EMF API is used, only the top-level objects are properly created and stored. Any contained objects are not stored properly. When the Java EMF API is used, everything works as expected. On an XMI resource, everything works as expected, regardless of whether the Java or Epsilon EMF API is used.

* The third mode (`head2head`) uses the Java and Epsilon EMF API head to head at each step for debugging purposes so that the results at each step can be compared. After creating a `Shelf`, a `Box` named "JavaBox" contained in it with the Java EMF API, and another `Box` named "EpsilonBox" is created and added to the same `Shelf` by means of the Epsilon EMF API. The result is similar to the outcome from the other two modes. The only difference appears to be under eStorage where the Epsilon EMF API appears to add extra information which potentially prevents NeoEMF from working properly.