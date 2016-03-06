package edu.nyu.cs.javagit.test.utilities;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitInit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;

public class GitRepositoryBuilder
{
    private Deletor deletor = new DummyDeletor();
    private Collection<RepositoryBuilderOperation> operations = new ArrayList<>();
    private String currentFolder = ".";

    public GitRepositoryBuilder()
    {
    }

    public GitRepositoryBuilder(RealDeletor deletor)
    {
        this.deletor = deletor;
    }

    /**
     * Adds file to be created, based on last folder added.
     * <p>
     * Ver {@link GitRepositoryBuilder#addFolder(java.lang.String)}
     * @param fileName
     * @param contents
     * @return
     */
    public GitRepositoryBuilder addFile(String fileName, String contents)
    {
        operations.add(new FileAdder(currentFolder, fileName, contents));
        return this;
    }

    /**
     * Folder to be created. Subsequent file creation will be inside this folder
     * @param folder
     * @return
     */
    public GitRepositoryBuilder addFolder(String folder)
    {
        this.currentFolder = folder;
        operations.add(new FolderCreator(currentFolder));
        return this;
    }

    public File build()
            throws IOException, JavaGitException
    {
        File repositoryFolder = Files.createTempDirectory(new File(System.getProperty("java.io.tmpdir")).toPath(), "SwissArmyJavaGit").toFile();
        new GitInit().init(repositoryFolder);
        deletor.add(repositoryFolder);

        for (RepositoryBuilderOperation op : operations)
        {
            op.setBaseFolder(repositoryFolder);
            op.execute();
        }

        return repositoryFolder;
    }

    private class FileAdder
            implements RepositoryBuilderOperation
    {
        private final String folder;
        private String fileName;
        private String contents;
        private File baseFolder;

        public FileAdder(String folder, String fileName, String contents)
        {
            this.folder = folder;
            this.fileName = fileName;
            this.contents = contents;
        }

        @Override
        public void execute()
                throws IOException
        {
            FileUtilities.createFile(new File(baseFolder, folder), fileName, contents);
        }

        @Override
        public void setBaseFolder(File folder)
        {
            this.baseFolder = folder;
        }

    }

    private interface RepositoryBuilderOperation
    {

        void execute()
                throws IOException;

        void setBaseFolder(File folder);

    }

    private class FolderCreator
            implements RepositoryBuilderOperation
    {
        private final String folder;
        private File baseFolder;

        public FolderCreator(String folder)
        {
            this.folder = folder;
        }

        @Override
        public void execute()
                throws IOException
        {
            File effectiveFolder = new File(baseFolder, folder);
            if ( ! effectiveFolder.mkdirs() ) {
                throw new IOException("Can't create folder '" + effectiveFolder.getAbsolutePath() + "'" );
            }
        }

        @Override
        public void setBaseFolder(File folder)
        {
            this.baseFolder = folder;
        }

    }
}
