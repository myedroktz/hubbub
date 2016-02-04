package com.grailsinaction

class PhotoUploadCommand{
    byte[] photo //Holds uploaded photo data
    String loginId
}

class ImageController {
    // Upload image to filesystem
    def rawUpload() {
    // a Spring MultipartFile
        def mpf = request.getFile('photo')
        if (!mpf?.empty && mpf.size < 1024*200) {
            mpf.transferTo(new File(
                    "/hubbub/images/${params.loginId}/mugshot.gif"))
        }
    }

    // Upload image to database
    def upload(PhotoUploadCommand puc){
        def user = User.findByLoginId(puc.loginId)
        user.profile.photo = puc.photo
        redirect controller: "user", action: "profile", id: puc.loginId
    }

    def form(){
        //passes list of users to the view
        [userList: User.list()]
    }

    def renderImage(String id) {
        def user = User.findByLoginId(id)
        if (user?.profile?.photo) {
            response.setContentLength(user.profile.photo.size())
            response.outputStream.write(user.profile.photo)
        } else {
            response.sendError(404)
        }
    }

    def tiny(String id) {
        def user = User.findByLoginId(id)
        if (user?.profile?.photo) {
            response.setContentLength(user.profile.photo.size())
            response.outputStream.write(user.profile.photo)
        } else {
            response.sendError(404)
        }
    }

    def index() {}
}
