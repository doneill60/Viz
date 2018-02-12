/**
 * 
 */
 
package cs1980viz;

import java.util.Comparator;

public class FileDataComparator implements Comparator<FileData>{
	@Override
	public int compare(FileData fd1, FileData fd2){
		if(fd1.similarity < fd2.similarity){
			return 1;
		}
		if(fd1.similarity > fd2.similarity){
			return -1;
		}
		return 0;
	}
}

