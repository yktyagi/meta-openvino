SUMMARY = "OpenVINO(TM) Toolkit - Open Model Zoo repository"
HOMEPAGE = "https://github.com/opencv/open_model_zoo"
DESCRIPTION = "This repository includes optimized deep learning \
models and a set of demos to expedite development of high-performance \
deep learning inference applications."

SRC_URI = "git://github.com/opencv/open_model_zoo.git;protocol=https;branch=releases/2025/1 \
           file://0001-use-oe-gflags.patch \
           "

SRCREV = "cf0b5623388ed24b9ed7965d22535d2038447aed"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327 \
"

inherit cmake

S = "${UNPACKDIR}/${BP}"

OECMAKE_SOURCEPATH = "${S}/demos"

DEPENDS += "openvino-inference-engine opencv gflags"

RDEPENDS:${PN} += " \
                   python3-decorator \
                   python3-defusedxml \
                   python3-networkx \
                   python3-protobuf \
                   python3-requests \
                   python3-pyyaml \
                   python3-numpy \
                   bash \
"

#COMPATIBLE_HOST = '(x86_64).*-linux'
COMPATIBLE_HOST:libc-musl = "null"

EXTRA_OECMAKE += " \
                 -DENABLE_SAMPLES=ON \
                 "

do_install(){
        install -d ${D}${libdir}
        install -d ${D}${bindir}
        install -d ${D}${datadir}/openvino/open-model-zoo/tools
        install -d ${D}${datadir}/openvino/open-model-zoo/demos
        if [ "${TARGET_ARCH}" = "x86_64" ];then
            cp -rf ${B}/intel64/Release/*.a ${D}${libdir}
            cp -rf ${B}/intel64/Release/*_demo* ${D}${bindir}
        else
            cp -rf ${B}/${TARGET_ARCH}/Release/*.a ${D}${libdir}
            cp -rf ${B}/${TARGET_ARCH}/Release/*_demo* ${D}${bindir}
        fi
        cp -rf ${S}/models ${D}${datadir}/openvino/open-model-zoo
        cp -rf ${S}/demos ${D}${datadir}/openvino/open-model-zoo
        cp -rf ${S}/tools/model_tools ${D}${datadir}/openvino/open-model-zoo/tools
}

FILES:${PN} += "${datadir}/openvino"
