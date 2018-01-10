@echo ============ Start building R2QServer ============
@echo off

@set WORKING_DIR=%CD%
@set BUILD_DIR=%WORKING_DIR%\build
@echo Current working dir: %WORKING_DIR%
@echo Current build dir: %BUILD_DIR%

@rem Clean previous build output
if exist %BUILD_DIR% (
    @echo Removing build folder...
    del /f /s /q %BUILD_DIR% 1>nul
    rmdir /s /q %BUILD_DIR%
)

@echo Creating folder structure...
mkdir %BUILD_DIR%
mkdir %BUILD_DIR%\libs
mkdir %BUILD_DIR%\libs\ffi
mkdir %BUILD_DIR%\libs\luasocket
mkdir %BUILD_DIR%\libs\mapping
mkdir %BUILD_DIR%\libs\oop
mkdir %BUILD_DIR%\logs
mkdir %BUILD_DIR%\modules
mkdir %BUILD_DIR%\modules\channels

@echo Compiling R2QServer lua sources...
luac -o %BUILD_DIR%\R2QServer.lua %WORKING_DIR%\R2QServer.lua

luac -o %BUILD_DIR%\modules\Server.lua %WORKING_DIR%\modules\Server.lua
luac -o %BUILD_DIR%\modules\Cache.lua %WORKING_DIR%\modules\Cache.lua
luac -o %BUILD_DIR%\modules\Logger.lua %WORKING_DIR%\modules\Logger.lua
luac -o %BUILD_DIR%\modules\QuikDataManager.lua %WORKING_DIR%\modules\QuikDataManager.lua
luac -o %BUILD_DIR%\modules\RequestManager.lua %WORKING_DIR%\modules\RequestManager.lua

luac -o %BUILD_DIR%\modules\channels\DataTransferChannel.lua %WORKING_DIR%\modules\channels\DataTransferChannel.lua
luac -o %BUILD_DIR%\modules\channels\PipeChannel.lua %WORKING_DIR%\modules\channels\PipeChannel.lua
luac -o %BUILD_DIR%\modules\channels\SocketChannel.lua %WORKING_DIR%\modules\channels\SocketChannel.lua

@echo Compiling JSON.lua library...
luac -o %BUILD_DIR%\libs\mapping\JSON.lua %WORKING_DIR%\libs\mapping\JSON.lua

@echo Compiling middleclass.lua library...
luac -o %BUILD_DIR%\libs\oop\middleclass.lua %WORKING_DIR%\libs\oop\middleclass.lua

@echo Copy ffi dlls...
xcopy /S %WORKING_DIR%\libs\ffi %BUILD_DIR%\libs\ffi

@echo Copy luasocket library...
xcopy /S %WORKING_DIR%\libs\luasocket %BUILD_DIR%\libs\luasocket

@echo Compiling lua-files of the luasocket library...
cd %BUILD_DIR%\libs\luasocket\lua
luac -o socket.lua socket.lua
luac -o mime.lua mime.lua
luac -o ltn12.lua ltn12.lua

cd %BUILD_DIR%\libs\luasocket\lua\socket
luac -o ftp.lua ftp.lua
luac -o http.lua http.lua
luac -o smtp.lua smtp.lua
luac -o tp.lua tp.lua
luac -o url.lua url.lua

cd %WORKING_DIR%
@echo ============ Finished building R2QServer compilation ============