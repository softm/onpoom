package com.quickex.core.util.file;


import com.quickex.core.config.KoConfig;
import com.quickex.core.constant.Constants;
import com.quickex.core.exception.file.FileNameLengthLimitExceededException;
import com.quickex.core.exception.file.FileSizeLimitExceededException;
import com.quickex.core.exception.file.InvalidExtensionException;
import com.quickex.core.util.DateUtils;
import com.quickex.core.util.StringUtils;
import com.quickex.core.util.uuid.IdUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * File upload tool class
 *
 * @author ffzh
 */
public class FileUploadUtils
{
    /**
     * Default size 50m
     */
    public static final long DEFAULT_MAX_SIZE = 200 * 1024 * 1024;

    /**
     * The default file name has a maximum length of 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * Default upload address
     */
    private static String defaultBaseDir = KoConfig.getProfile();

    public static void setDefaultBaseDir(String defaultBaseDir)
    {
        FileUploadUtils.defaultBaseDir = defaultBaseDir;
    }

    public static String getDefaultBaseDir()
    {
        return defaultBaseDir;
    }

    /**
     * File upload with default configuration
     *
     * @param file
     * @throws Exception
     * @return
     */
    public static final String upload(MultipartFile file) throws IOException
    {
        try
        {
            return upload(getDefaultBaseDir(), file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**

     *Upload according to file path

     *

     *@ param basedir relative to the base directory of the application

     *@ param file uploaded file

     *@ return file relative path

     * @throws IOException

     */
    public static final String upload(String baseDir, MultipartFile file) throws IOException
    {
        try
        {
            return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**

     *File upload

     *

     *@ param basedir relative to the base directory of the application

     *@ param file uploaded file

     *@ param allowedextension upload file type

     *@ return returns the file name that was uploaded successfully

     *@ throws filesizelimitexceededexception if the maximum size is exceeded

     *@ throws filenamelengthlimitexceededexception file name is too long

     *@ throws IOException, such as error in reading and writing files

     *@ throws invalidextensionexception file verification exception

     */
    public static final String upload(String baseDir, MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
            InvalidExtensionException
    {
        int fileNamelength = file.getOriginalFilename().length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH)
        {
            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }

        assertAllowed(file, allowedExtension);

        String fileName = extractFilename(file);

        File desc = getAbsoluteFile(baseDir, fileName);
        file.transferTo(desc);
        String pathFileName = getPathFileName(baseDir, fileName);
        return pathFileName;
    }



    /**

     *Upload according to file path

     *

     *@ param basedir relative to the base directory of the application

     *@ param file uploaded file

     *@ return file relative path

     * @throws IOException

     */
    public static final HashMap uploadZip(String baseDir, MultipartFile file) throws IOException
    {
        try
        {
            return uploadZip(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage(), e);
        }
    }
    /**

     *Zip file upload

     *

     *@ param basedir relative to the base directory of the application

     *@ param file uploaded file

     *@ param allowedextension upload file type

     *@ return returns the file name that was uploaded successfully

     *@ throws filesizelimitexceededexception if the maximum size is exceeded

     *@ throws filenamelengthlimitexceededexception file name is too long

     *@ throws IOException, such as error in reading and writing files

     *@ throws invalidextensionexception file verification exception

     */
    public static final HashMap uploadZip(String baseDir, MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
            InvalidExtensionException
    {
        int fileNamelength = file.getOriginalFilename().length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH)
        {
            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }

        assertAllowed(file, allowedExtension);

        String fileName = extractFilename(file);

        File desc = getAbsoluteFile(baseDir, fileName);
        file.transferTo(desc);
        String pathFileName = getPathFileName(baseDir, fileName);
        HashMap result = new HashMap();
        result.put("file", desc);//
        result.put("fileName", fileName);//（）
        result.put("webFileName", pathFileName);//
        return result;
    }

    /**
     * Encoded file name
     */
    public static final String extractFilename(MultipartFile file)
    {
        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        fileName = DateUtils.datePath() + "/" + IdUtils.fastSimpleUUID() + "." + extension;
        return fileName;
    }

    public static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException
    {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.exists())
        {
            if (!desc.getParentFile().exists())
            {
                desc.getParentFile().mkdirs();
            }
        }
        return desc;
    }

    public static final String getPathFileName(String uploadDir, String fileName) {
        int dirLastIndex = KoConfig.getProfile().length() + 1;
        String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
        String pathFileName = Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
        return pathFileName;
    }

    /**
     * File size verification
     *
     * @param file
     * @return
     * @throws FileSizeLimitExceededException
     * @throws InvalidExtensionException
     */
    public static final void assertAllowed(MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, InvalidExtensionException
    {
        long size = file.getSize();
        if (DEFAULT_MAX_SIZE != -1 && size > DEFAULT_MAX_SIZE)
        {
            throw new FileSizeLimitExceededException(DEFAULT_MAX_SIZE / 1024 / 1024);
        }

        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension))
        {
            if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeTypeUtils.FLASH_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeTypeUtils.VIDEO_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidVideoExtensionException(allowedExtension, extension,
                        fileName);
            }
            else
            {
                throw new InvalidExtensionException(allowedExtension, extension, fileName);
            }
        }

    }

    /**
     * Determine whether the MIME type is an allowed MIME type
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static final boolean isAllowedExtension(String extension, String[] allowedExtension)
    {
        for (String str : allowedExtension)
        {
            if (str.equalsIgnoreCase(extension))
            {
                return true;
            }
        }
        return false;
    }

    /**

     *Gets the suffix of the file name

     *

     *@ param file form file

     *@ return suffix

     */
    public static final String getExtension(MultipartFile file)
    {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension))
        {
            extension = MimeTypeUtils.getExtension(file.getContentType());
        }
        return extension;
    }
}
