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
    

    static main(String... args) {
        def path = args.size() == 0 ? "." : args[0]
        
        def dir = new File(path)
        def d = buildGitIgnoreTree(dir)
        def result = d.analyzeTree()
//        result.each {
//			print it.key + "\n"
//			it.value.each { val ->
//				print "  " + val.toString() + "\n"
//			}
//        }
        

//        def p = "/.*"
//        def matched = java.util.regex.Pattern.matches(p, "/asdf")
//        print matched        
        
//        dir.eachFileRecurse FileType.FILES, {
//            if(it.name == ".gitignore") {
//                files[it.getCanonicalFile()] = new GitIgnore(it.getCanonicalFile())
//            } 
//        }
//        print "l " + d +"\n"
//        print "l " + files +"\n"
//        print "c " + dir.getCanonicalFile()+"\n"
//        print "c " + dir.getCanonicalFile().getParent()+"\n"
//        print dir.getParentFile().getName()
    }

}