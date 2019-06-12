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
VidCEP can be run from the controller package. It has a run engine file from where the system can be intialised.  

### Reference
Please cite the paper if you are using any part of this work:

[Preprint Version]https://www.researchgate.net/publication/333703842_VidCEP-Complex_Event_Processing_Framework_to_Detect_Spatiotemporal_Event_Patterns_in_Video_Streams

### Contact
In case of any queries or issue please connect with me at piyush.yadav@insight-centre.org
