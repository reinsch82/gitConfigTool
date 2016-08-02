package org.reinhold.git.ignore

import groovy.io.FileType

class GitIgnoreTool {

    static buildGitIgnoreTree(dir) {
        Dir d = new Dir(dir)
        dir.eachFile FileType.FILES, {
            if(it.name == ".gitignore") {
                d.ignoreFile = new GitIgnore(it.getCanonicalFile())
            }
        }
        dir.eachDir {
            if(it.name != ".git") {
                d.dirs << buildGitIgnoreTree(it)
            }
        }
        return d
    }
    
	static printTree(Dir dir, indent = "", prepend = "") {
		if(dir.ignoreFile instanceof GitIgnore) {
			println indent + "* " + prepend + "/" + dir.dir.name
			indent = "  " + indent
			dir.ignoreFile.content.each {
				println indent + "  " + it
			}
			dir.dirs.each { printTree(it, indent) }
		}
		else {
			dir.dirs.each { printTree(it, indent, prepend + "/" + dir.dir.name) }
		}
	}

    static main(String... args) {
		def path = args.find{!it.startsWith("--")}
        path = path != null ? path : "."
		def isOptimize = args.contains("--optimize")
        def isPrint = args.contains("--print")
		def dir = new File(path)
        def d = buildGitIgnoreTree(dir)
        if(isPrint) {
			printTree(d)	
		}
		else
		if(isOptimize) {
			
		}
		else {
			d.analyzeTree()
		}
    }

}