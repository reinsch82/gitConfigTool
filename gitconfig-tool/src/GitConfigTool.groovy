
import groovy.io.FileType

class GitConfigTool {

	static main(args) {
		
		def list = []
		
		def dir = new File(".")
		dir.eachFileRecurse (FileType.FILES) { file ->
		  list << file
		}
		print list
		print dir.getParent()
		print dir.getParentFile().getName()
	}

}
