SDCube Programming Library (Port)

Software for the creation and manipulation of Semantically Typed Data Cubes:
Structured storage of large biological data sets.

Copyright (C) 2011 Bjorn Millard <bjornmillard@gmail.com>
License: LGPL v3, see LICENSE.txt and COPYING.txt

# Introduction

This is a port of the original SDCube API library by Gully Burns at the 
Information Sciences Institute. Our goals were to create a functional 
working copy that we could interrogate and use as the basis for new 
development. We will not be further developing this model directly 'as 
is' but will be repurposing the basic idea and funcitonality to 
semantic web elements. 

The SDCube webpage is at http://www.semanticbiology.com/software/sdcube

# Requirements

* Java 1.7 - http://www.java.com/

* Java HDF5 2.11 - http://www.hdfgroup.org/products/java/release/download.html

Note that you must manually include the native libraries as run time variables:

```
  -Djava.library.path=/Applications/HDFView.app/Contents/Resources/lib
```

Also, we have adopted Maven as our build environment and HDF5 do not provide
Maven builds of the required `*.jar` files for this build. We have included
these within our local Nexus repository: 

```
  http://hugin.isi.edu:8180/nexus/content/repositories/thirdparty/
```

This is not a good solution going forward, but will serve the temporary needs 
of this port. 

# Running the SDCube Tests

To check the installation is working, we recommend running the unit tests under
`edu.harvard.sorgerlab.sdcubeio`. Again, we are using this product primarily as 
a base to understand the use of HDF5 in this context. 
