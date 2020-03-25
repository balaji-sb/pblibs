package com.pblibs.utility;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.pblibs.base.PBApplication;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import static com.pblibs.utility.PBConstants.*;

public class PBFileOperations {

    private static final String GOOGLE_PHOTS_CONTENT = "com.google.android.apps.photos.content";
    private static final String MEDIA_DOCUMENT_CONTENT = "com.android.providers.media.documents";
    private static final String DOWNLOAD_DOCUMENT_CONTENT = "com.android.providers.downloads.documents";
    private static final String EXTERNAL_STORAGE_DOCUMENT_CONTENT = "com.android.externalstorage.documents";
    private static final String FILE = "file";
    private static final String CONTENT = "content";
    private static PBFileOperations mInstance;
    private Context mContext;

    private PBFileOperations() {
        mContext = PBApplication.getInstance().getContext();
    }

    public static PBFileOperations getInstance() {
        if (mInstance == null) {
            mInstance = new PBFileOperations();
        }
        return mInstance;
    }

    /**
     * Create Directory with directoryName and storage device
     *
     * @param directoryName
     * @param isExternal
     * @return
     */

    public File createDirectory(String directoryName, boolean isExternal) {
        if (directoryName.isEmpty() || directoryName == null) {
            directoryName = PBApplication.TAG;
        }
        File directory;
        if (isExternal) {
            directory = new File(Environment.getExternalStorageDirectory() + BACK_SLASH + directoryName);
        } else {
            directory = new File(Environment.getDataDirectory() + BACK_SLASH + directoryName);
        }
        if (!directory.exists()) {
            directory.mkdir();
        }
        return directory;
    }

    /**
     * Write data into file
     *
     * @param directory
     * @param fileName
     * @param data
     * @param isExternal
     * @param isAppend
     * @return
     */

    public boolean writeToFile(String directory, String fileName, Object data, boolean isExternal, boolean isAppend) {
        try {
            File isDirectory = createDirectory(directory, isExternal);
            if (isDirectory == null) {
                return false;
            }
            File file = new File(directory, fileName);
            Writer output = new BufferedWriter(new FileWriter(file, isAppend));
            output.write(data.toString());
            output.flush();
            output.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            //do nothing
        }
    }

    /**
     * Get Files based on directory
     *
     * @param directoryName
     * @param isExternal
     * @return
     */

    public File[] getFilesByDirectory(String directoryName, boolean isExternal) {
        File directory = createDirectory(directoryName, isExternal);
        if (directory == null) {
            return null;
        } else {
            return directory.listFiles();
        }
    }

    /**
     * Download file from url and write into internal or external storage
     *
     * @param directory
     * @param fileName
     * @param isExternal
     * @param path
     * @param urlPath
     * @return
     */

    public String downloadFileFromURL(String directory, String fileName, boolean isExternal, String path,
                                      String urlPath) {
        try {
            int count;
            URL url = new URL(urlPath);
            URLConnection conexion = url.openConnection();
            conexion.connect();
            InputStream input = new BufferedInputStream(url.openStream());
            File isDirectory = createDirectory(directory, isExternal);
            if (isDirectory == null) {
                return null;
            }
            File file = new File(directory, fileName);
            OutputStream outputStream = new FileOutputStream(file);
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                //publishProgress("" + (int) ((total * 100) / lenghtOfFile)); to publish progress of download file we
                // use this
                outputStream.write(data, 0, count);
            }
            outputStream.flush();
            outputStream.close();
            return file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Rename file with old name to new name
     *
     * @param directoryName
     * @param oldName
     * @param newName
     * @param isExternal
     * @return
     */

    public File renameFile(String directoryName, String oldName, String newName, boolean isExternal) {
        File directory;
        if (isExternal) {
            directory = new File(Environment.getExternalStorageDirectory() + BACK_SLASH + directoryName);
        } else {
            directory = new File(Environment.getDataDirectory() + BACK_SLASH + directoryName);
        }
        File toFile = null;
        if (directory.exists()) {
            File from = new File(directory, oldName);
            toFile = new File(directory, newName);
            if (from.exists())
                from.renameTo(toFile);
        }
        return toFile;
    }


    /**
     * Check current version is kitkat and above
     *
     * @return
     */

    public boolean isAboveKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * Get uri related content real local file path.
     *
     * @param uri
     * @return
     */

    public String getUriRealPath(Uri uri) {
        if (isAboveKitKat()) {
            return getUriRealPathAboveKitkat(uri);
        } else {
            return getImageRealPath(mContext.getContentResolver(), uri, null);
        }
    }

    /**
     * To get the uri real path for above kitkat
     *
     * @param uri
     * @return
     */

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String getUriRealPathAboveKitkat(Uri uri) {
        String ret = "";
        if (uri != null) {
            if (isContentUri(uri)) {
                if (isGooglePhotoDoc(uri.getAuthority())) {
                    ret = uri.getLastPathSegment();
                } else {
                    getImageRealPath(mContext.getContentResolver(), uri, null);
                }
            } else if (isFileUri(uri)) {
                ret = uri.getPath();
            } else if (isDocumentUri(uri)) {
                String documentId = DocumentsContract.getDocumentId(uri);
                String uriAuthority = uri.getAuthority();
                if (isMediaDoc(uriAuthority)) {
                    String[] idArr = documentId.split(":");
                    if (idArr.length == 2) {
                        String docType = idArr[0];
                        String realDocId = idArr[1];
                        Uri mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        switch (docType) {
                            case IMAGE:
                                mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                                break;
                            case VIDEO:
                                mediaContentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                                break;
                            case AUDIO:
                                mediaContentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                                break;
                        }
                        String whereClause = MediaStore.Images.Media._ID + SYMBOL_EQUALS + realDocId;
                        ret = getImageRealPath(mContext.getContentResolver(), mediaContentUri, whereClause);
                    }
                } else if (isDownloadDoc(uriAuthority)) {
                    Uri downloadUri = Uri.parse(PUB_DOWNLOADS);
                    Uri downloadUriAppendId = ContentUris.withAppendedId(downloadUri, Long.valueOf(documentId));
                    ret = getImageRealPath(mContext.getContentResolver(), downloadUriAppendId, null);
                } else if (isExternalStoreDoc(uriAuthority)) {
                    String[] idArr = documentId.split(":");
                    if (idArr.length == 2) {
                        String type = idArr[0];
                        String realDocId = idArr[1];
                        if (PRIMARY.equalsIgnoreCase(type)) {
                            ret = Environment.getExternalStorageDirectory() + BACK_SLASH + realDocId;
                        }
                    }
                }
            }
        }
        return ret;
    }

    /**
     * Return uri represented document file real local path.
     *
     * @param contentResolver
     * @param uri
     * @param whereClause
     * @return
     */

    private String getImageRealPath(ContentResolver contentResolver, Uri uri, String whereClause) {
        String ret = "";
        try {
            Cursor cursor = contentResolver.query(uri, null, whereClause, null, null);
            if (cursor != null) {
                boolean moveToFirst = cursor.moveToFirst();
                if (moveToFirst) {
                    String columnName = MediaStore.Images.Media.DATA;

                    if (uri.equals(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)) {
                        columnName = MediaStore.Images.Media.DATA;
                    } else if (uri.equals(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)) {
                        columnName = MediaStore.Audio.Media.DATA;
                    } else if (uri.equals(MediaStore.Video.Media.EXTERNAL_CONTENT_URI)) {
                        columnName = MediaStore.Video.Media.DATA;
                    }
                    int imageColumnIndex = cursor.getColumnIndex(columnName);
                    ret = cursor.getString(imageColumnIndex);
                    cursor.close();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ret;
    }

    /**
     * Check whether this uri represent a document or not.
     *
     * @param uri
     * @return
     */

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean isDocumentUri(Uri uri) {
        boolean ret = false;
        if (mContext != null && uri != null) {
            ret = DocumentsContract.isDocumentUri(mContext, uri);
        }
        return ret;
    }


    /**
     * Check whether this uri is a content uri or not.
     *
     * @param uri
     * @return
     */

    private boolean isContentUri(Uri uri) {
        boolean ret = false;
        if (uri != null) {
            String uriSchema = uri.getScheme();
            if (CONTENT.equalsIgnoreCase(uriSchema)) {
                ret = true;
            }
        }
        return ret;
    }

    /**
     * Check whether this uri is a file uri or not.
     *
     * @param uri
     * @return
     */

    private boolean isFileUri(Uri uri) {
        boolean ret = false;
        if (uri != null) {
            String uriSchema = uri.getScheme();
            if (FILE.equalsIgnoreCase(uriSchema)) {
                ret = true;
            }
        }
        return ret;
    }

    /**
     * Check whether this document is provided by ExternalStorageProvider.
     *
     * @param uriAuthority
     * @return
     */

    private boolean isExternalStoreDoc(String uriAuthority) {
        boolean ret = false;
        if (EXTERNAL_STORAGE_DOCUMENT_CONTENT.equals(uriAuthority)) {
            ret = true;
        }
        return ret;
    }

    /**
     * Check whether this document is provided by DownloadsProvider.
     *
     * @param uriAuthority
     * @return
     */

    private boolean isDownloadDoc(String uriAuthority) {
        boolean ret = false;
        if (DOWNLOAD_DOCUMENT_CONTENT.equals(uriAuthority)) {
            ret = true;
        }
        return ret;
    }

    /**
     * Check whether this document is provided by MediaProvider.
     *
     * @param uriAuthority
     * @return
     */

    private boolean isMediaDoc(String uriAuthority) {
        boolean ret = false;
        if (MEDIA_DOCUMENT_CONTENT.equals(uriAuthority)) {
            ret = true;
        }
        return ret;
    }

    /**
     * Check whether this document is provided by google photos.
     *
     * @param uriAuthority
     * @return
     */

    private boolean isGooglePhotoDoc(String uriAuthority) {
        boolean ret = false;
        if (GOOGLE_PHOTS_CONTENT.equals(uriAuthority)) {
            ret = true;
        }
        return ret;
    }

    public File createImageFile(String tempFileName, String directoryName, boolean isExternal) {
        try {
            File storageDir = createDirectory(directoryName, isExternal);
            File image = File.createTempFile(
                    tempFileName, /* prefix */
                    TYPE_JPG, /* suffix */
                    storageDir      /* directory */
            );
            String imageFilePath = image.getAbsolutePath();
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Check if file is appended or new one
     *
     * @param directory
     * @param isExternal
     * @param date
     * @return
     */

    public boolean isFileAppend(String directory, boolean isExternal, String date) {
        File isDirectory = createDirectory(directory, isExternal);
        if (isDirectory == null) {
            return false;
        }
        boolean isAppend = false;
        File[] files = isDirectory.listFiles();
        for (int i = 0; i < files.length; i++) {
            Log.d("Files", "FileName:" + files[i].getName());
            if (files[i].getName().split(UNDER_SCORE).length > 1) {
                if (files[i].getName().split(UNDER_SCORE)[1].equalsIgnoreCase(date)) {
                    isAppend = true;
                }
            }
        }
        return isAppend;
    }

    /**
     *  Get File by descriptor
     *  Specially for Android Q to use with retrieve audio file information.
     * @param uri
     * @param fileName
     * @return
     */


    public File getFileByDescriptor(Uri uri, String fileName) {
        if (uri == null)
            return null;
        FileInputStream input = null;
        FileOutputStream output = null;
        String filePath = new File(mContext.getCacheDir(), fileName).getAbsolutePath();
        try {
            ParcelFileDescriptor pfd = mContext.getContentResolver().openFileDescriptor(uri, "r");
            if (pfd == null)
                return null;
            FileDescriptor fd = pfd.getFileDescriptor();
            input = new FileInputStream(fd);
            output = new FileOutputStream(filePath);
            int read;
            byte[] bytes = new byte[4096];
            while ((read = input.read(bytes)) != -1) {
                output.write(bytes, 0, read);
            }
            return new File(filePath);
        } catch (IOException ignored) {
            ignored.printStackTrace();
            return null;
        }
    }
}
