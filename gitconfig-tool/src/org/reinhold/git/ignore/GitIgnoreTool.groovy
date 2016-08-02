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
        d.analyzeTree()
    }

}