---
layout: index
title: Cookbook Examples
---



The usage of JavaGit library can be broadly divided into to two parts:

  1. JavaGit Object API 
  2. JavaGit Command API

**JavaGit Object API**

  * Setting up the JavaGit Library
  * Initializing a new project
  * DotGit versus WorkingTree 
  * Adding files to the repository
  * Commiting files
  * Managing branches
  * Retrieving git logs
  * Complete Example
**JavaGit Command API**

  * How to use JavaGit Commands

* * *

##  **Setting up the JavaGit Library**

If you have multiple versions of git or the git binary is not on your system
path, then you may want to setup the git binary to be used by JavaGit.

    
    
    // Make sure you have JavaGit added as an external jar in your project
    JavaGitConfiguration.setGitPath("/usr/bin/");
    
    // Print the git version:  1.5.1
    System.out.println(JavaGitConfiguration.getGitVersion()); 
    

##  **Initializing a new project.**

    
    
    // Create a new directory to be used as a git repository or point to an exisiting directory.
    File repositoryDirectory = new File("/path/to/your_repo_directory");
    
    //get the instance of the dotGit Object
    dotGit = DotGit.getInstance(repositoryDirectory);
    
    //Initialize the repository ,similar to git init
    dotGit.init();
    

##  **DotGit versus WorkingTree**

The WorkingTree represents the checkout of the current working branch. The
DotGit represents the .git directory in the root directory of the working
tree; i.e. the git repository.

    
    
    File repositoryDirectory = new File("/path/to/your_repo_directory");
    
    // Get the instance of the DotGit Object
    DotGit dotGit = DotGit.getInstance(repositoryDirectory);
    
    // Get the current working tree from the git repository
    WorkingTree wt = dotGit.getWorkingTree();
    

##  **Adding files to the repository**

    
    
    // The "/path/to/working/tree" is the directory containing the .git directory
    File workingTreePath = new File("/path/to/working/tree");
    WorkingTree wt = WorkingTree.getInstance(workingTreePath);
    
    GitFile file = wt.getFile(new File("path/relative/to/workingtree/root.txt"));
    
    GitAddResponse ar = wt.add();
    

##  **Commiting files**

Git manages content, not files. So, add new or changed files before performing
a commit.

    
    
    File file = new File("FileToCreate.txt");
    
    // Create the file somewhere in the working tree
    ...
    
    File workingTreePath = new File("/path/to/working/tree");
    WorkingTree wt = WorkingTree.getInstance(workingTreePath);
    
    // Add modified content and commit
    wt.add();
    wt.commit("First commit to the git repository");
    

##  **Managing branches**

    
    
    // Get the instance of the dotGit Object
    File repositoryDirectory = new File("/path/to/your_repo_directory");
    DotGit dotGit = DotGit.getInstance(repositoryDirectory);
    
    // Get the current working tree
    WorkingTree wt = dotGit.getWorkingTree();
    
    // Getting the current working branch
    Ref master = wt.getCurrentBranch();
    
    // Print the name of the current branch, "master" in this case
    System.out.println("Current branch is:  " + master);
    
    // Creating a new branch 
    Ref experimental = dotGit.createBranch("experimental");
    
    // Change current working branch to the newly created branch
    wt.checkout(experimental);
    
    // Print the name of the current branch, now it is "experimental"
    System.out.println("Current branch is:  " + wt.getCurrentBranch());
    
    // Deleting a branch, remember to change the current branch
    wt.checkout(master);
    dotGit.deleteBranch(experimental, true);
    

##  **Retrieving git logs**

    
    
    // Get the instance of the dotGit Object
    File repositoryDirectory = new File("/path/to/your_repo_directory");
    DotGit dotGit = DotGit.getInstance(repositoryDirectory);
    
    // Print commit messages of the current branch
    for (Commit c : dotGit.getLog()) {
        System.out.println(c.getMessage());
    }
    

##  **Complete example**

    
    
    Example : [JavaGitDemo.java](../code_samples/JavaGitDemo.java)
    

  

* * *

##  **How to use JavaGit Commands**

All the JavaGit Commands are implemented in a uniform manner. Below is an
example of using the GitStatus command to get the number of untracked files in
the repository and the GitAdd command to add a new file to the repository.

    
    
    File workingTreePath = new File("/path/to/working/tree");
    File newFile = new File("Another/File/To/Create.txt");
    
    // Create the file
    ...
    
    // Use the GitStatus Command API to get number of untracked files.
    GitStatus gitStatus = new GitStatus();
    GitStatusResponse status = gitStatus.status(workingTreePath);
    int numberOfUntrackedFiles = status.getUntrackedFilesSize();
    
    // Add the file 
    GitAdd add = new GitAdd();
    add.add(workingTreePath, newFile);
    
    // Get the status of the Another/File directory
    File anotherFileDir = new File("Another/File");
    GitStatusOptions options = new GitStatusOptions();
    options.setOptOnly(true);
    status = gitStatus.status(workingTreePath, options, anotherFileDir);
    

