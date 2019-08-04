# VidCEP
This is initial version of complex event processing framework to detect spatiotemporal event patterns in video streams.
Please read the blogpost and paper  (coming soon) for details.

### Requirements
VidCEP prototype is implemented in Java. Its python version will be released in future.

**Software Requirements**
You will need the following to be installed before running the system:
1. Java 8
2. Cuda 9.1 (Cuda 10 also works)
3. CudNN

**Hardware Requirements**
1. GPU (tested on Nvidia Titan Xp with 12 GB RAM)
2. The system can run on CPU also but you need to change the pom file for CPU related DL4j library. The CPU performance is very low.
3. Atleast 16 GB RAM


### Dependencies
The pom file is provided with all the required dependencies. The system runs over Deeplearning 4j deeplearning library. Except this it requires opencv, jgrapht, ffmpeg and sl4j. 
> Please make sure that you are using the same version of all the required dependencies for deeplearning4j. For example if dl4j version 1.0.0 beta is used then make sure all other required dependencies like nd4j etc. should have the same version number. For CPU replace all dl4j cuda required dependencies with normal CPU version. For more please follow the given link: https://deeplearning4j.org/docs/latest/deeplearning4j-config-gpu-cpu

### Running the VidCEP Engine
VidCEP can be run from the controller package. It has a run engine file from where the system can be intialised. A small video clip is provided in Dataset and publisher folder for initial analysis. Presently the subscriptioins are in form of Object, Object-Object(O-O) relation with windows and publisher information. The SQL query parser is coming soon. The subscription format provided works in the same way with all the functionalties discussed in paper.   

### Spatiotemporal Operators
The intial spatiotemporal operators list are as follows:
1. Direction: Left, Right, Front, Back
2. Temporal: SEQ, CONJ, EQ
#### Domain Specific (Traffic Management)
We have created more complex spatiotemporal opertors and motivate different users to create operators using logic rules provided in the VidCEP system. 

3. Pass By

The query operator ‘Pass By’ is defined as ‘change in relative position of the object (back-front) in the same direction of motion’. In Fig. 8, two frames of a video are shown at time ti and ti+j such that ti< ti+j. We see that relative position of object o_1 was ‘back’ of o_2 at ti which becomes ‘front’ at ti+j. This signifies that object o_1  crosses the o_2 in i+jth time instance. Thus, as per eq.1, we can write the ‘Pass By’ operator as:

4. Follows By
5. Lane Change
6. High Traffic Flow

### Reference
Please cite the paper if you are using any part of this work:

>Yadav, Piyush, Edward Curry. “Vid-CEP: Complex Event Processing Framework for Detecting Spatiotemporal Event Patterns in Video Streams ”
[Preprint Version- coming soon !!]

https://www.researchgate.net/publication/333703842_VidCEP-Complex_Event_Processing_Framework_to_Detect_Spatiotemporal_Event_Patterns_in_Video_Streams

### Contact
In case of any queries or issue please connect with me at piyush.yadav@insight-centre.org
