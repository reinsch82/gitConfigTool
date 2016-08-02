
import groovy.io.FileType

class GitIgnoreTool {
	static def files = [:]

	static fetchIgnore(dir) {
		dir.eachFile FileType.FILES, {
			if(it.name == ".gitignore") {
//				files[it.getCanonicalFile()] = new GitIgnore(it.getCanonicalFile())
			}
		}
	}  
	
	static main(String... args) {
		def path = args.size() == 0 ? "." : args[0]
		
		def dir = new File(path)
		
		dir.eachDir {
			dir.eachFile FileType.FILES, {
				if(it.name == ".gitignore") {
					files[it.getCanonicalFile()] = new GitIgnore(it.getCanonicalFile())
				}
			}
		}

		dir.eachFileRecurse FileType.FILES, {
			if(it.name == ".gitignore") {
				files[it.getCanonicalFile()] = new GitIgnore(it.getCanonicalFile())
			} 
		}
		print "l " + files.size() +"\n"
		print "l " + files +"\n"
//		print "c " + dir.getCanonicalFile()+"\n"
//		print "c " + dir.getCanonicalFile().getParent()+"\n"
//		print dir.getParentFile().getName()
	}

}
